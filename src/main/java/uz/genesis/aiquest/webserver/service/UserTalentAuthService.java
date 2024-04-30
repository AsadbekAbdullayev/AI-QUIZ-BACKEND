package uz.genesis.aiquest.webserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.genesis.aiquest.webserver.commons.exception.BadRequestException;
import uz.genesis.aiquest.webserver.commons.exception.GeneralApiException;
import uz.genesis.aiquest.webserver.commons.exception.UnauthorizedException;
import uz.genesis.aiquest.webserver.commons.security.JwtConfigurer;
import uz.genesis.aiquest.webserver.commons.security.JwtTokenObject;
import uz.genesis.aiquest.webserver.models.dto.PasswordResetDTO;
import uz.genesis.aiquest.webserver.models.dto.SignInByOauthProviderDTO;
import uz.genesis.aiquest.webserver.models.dto.SignInDTO;
import uz.genesis.aiquest.webserver.models.dto.UserTalentAuthDTO;
import uz.genesis.aiquest.webserver.models.dto.oauth.OauthAuthenticationService;
import uz.genesis.aiquest.webserver.models.dto.oauth.OneIdUserInfoResponseDTO;
import uz.genesis.aiquest.webserver.models.dto.res.JwtTokenResponse;
import uz.genesis.aiquest.webserver.models.entity.UserTalent;
import uz.genesis.aiquest.webserver.models.enums.EOauth2Provider;
import uz.genesis.aiquest.webserver.models.enums.UserType;
import uz.genesis.aiquest.webserver.models.mappers.mapstruct.UserTalentMapper;
import uz.genesis.aiquest.webserver.repository.UserTalentRepository;
import uz.genesis.aiquest.webserver.service.base.EmailService;
import uz.genesis.aiquest.webserver.utils.ErrorCodes;
import uz.genesis.aiquest.webserver.utils.Utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserTalentAuthService {
    private final UserTalentMapper userTalentMapper;
    private final UserTalentRepository userTalentRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Value(value = "${email.url.template}")
    private String emailURLTemplate;

    @Value(value = "${email.url.reset}")
    private String emailURLReset;

    public void signUp(UserTalentAuthDTO userTalentDTO) {
        log.info("signing up new user -> {}", userTalentDTO);
        if (userTalentRepository.existsByEmail(userTalentDTO.getEmail())) {
            throw new GeneralApiException("email_already_exists", ErrorCodes.ERR_BAD_REQUEST);
        }
        UserTalent userTalent = new UserTalent();
        userTalent.setFirstName(userTalentDTO.getFirstName());
        userTalent.setEmail(userTalentDTO.getEmail());
        userTalent.setPassword(userTalentDTO.getPassword());
        userTalent.setPassword(passwordEncoder.encode(userTalentDTO.getPassword()));
        userTalent.setEmailVerificationToken(UUID.randomUUID().toString());
        var savedUser = userTalentRepository.save(userTalent);
        log.info("user created , sending email ...");
        Executors.newSingleThreadExecutor().submit(() -> sendEmail(savedUser));
    }

    public JwtTokenResponse activateUser(String emailVerificationToken) {
        var notVerifiedUser = userTalentRepository.findByEmailVerificationToken(emailVerificationToken).orElseThrow(
                () -> new GeneralApiException("unknown_error")
        );
        notVerifiedUser.setIsEnabled(true);
        JwtTokenObject jwtTokenObject = JwtConfigurer.getJwtTokenObject(UserType.USER_TALENT,
                notVerifiedUser.getUsername(),
                notVerifiedUser.getEmail(),
                Map.of());
        var jwtToken = jwtTokenObject.getToken();
        notVerifiedUser.setEmailVerificationToken(null);
        userTalentRepository.save(notVerifiedUser);
        return JwtTokenResponse.builder().accessToken(jwtToken).build();
    }

    private void sendEmail(UserTalent savedUser) {
        try {
            emailService.sendProfileAccessUrl(savedUser.getEmail(), Utils.generateProfileAccessUrl(
                    emailURLTemplate,
                    savedUser.getEmailVerificationToken()));
            log.info("Email sent to email , {}", savedUser.getEmail());
        } catch (Exception e) {
            log.error("Couldn't sent email , please check in ", e);
        }
    }

    public JwtTokenResponse signIn(SignInDTO signInDTO) {
        try {
            var userTalent = userTalentRepository.findByEmail(signInDTO.getEmail())
                    .orElseThrow(() -> new BadRequestException("talent_email_not_found", ErrorCodes.ERR_BAD_REQUEST));
            if (!userTalent.isEnabled())
                throw new UnauthorizedException("talent_not_verified_email_yet");
            var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userTalent.getUsername(), signInDTO.getPassword()));
            var userDetails = (UserDetails) authentication.getPrincipal();
            JwtTokenObject jwtTokenObject = JwtConfigurer.getJwtTokenObject(UserType.USER_TALENT, userDetails.getUsername(), signInDTO.getEmail(), Map.of());
            var accessToken = jwtTokenObject.getToken();

            return JwtTokenResponse
                    .builder()
                    .accessToken(accessToken)
                    .build();

        } catch (BadCredentialsException exception) {
            throw new BadRequestException("email_or_password_error", ErrorCodes.ERR_BAD_REQUEST);
        }


    }

    private final OauthAuthenticationService oauthAuthenticationService;
    private final ObjectMapper objectMapper;

    public JwtTokenResponse signInByOauthProviders(SignInByOauthProviderDTO signInByOauthProviderDTO) {
        try {

            if (signInByOauthProviderDTO.getEOauth2Provider().equals(EOauth2Provider.ONE_ID)) {
                OneIdUserInfoResponseDTO userInfoFromOneId = oauthAuthenticationService.getUserInfoFromOneId(signInByOauthProviderDTO.getEOauth2Provider(), signInByOauthProviderDTO.getAuthorizationCode());
                Optional<UserTalent> optionalTalentByPin = userTalentRepository.findByOneIdPin(userInfoFromOneId.getPin());
                if (optionalTalentByPin.isPresent()) {
                    UserTalent userTalent = optionalTalentByPin.get();
                    JwtTokenObject jwtTokenObject = JwtConfigurer.getJwtTokenObject(UserType.USER_TALENT, userTalent.getUsername(), userTalent.getEmail(), Map.of());
                    var accessToken = jwtTokenObject.getToken();

                    return JwtTokenResponse
                            .builder()
                            .accessToken(accessToken)
                            .build();
                    //sign in
                }else {

                    String lastName = userInfoFromOneId.getSurName();
                    String firstName = userInfoFromOneId.getFirstName();
                    String autoGeneratedEmail = Utils.generateEmail(userInfoFromOneId.getFirstName(), userInfoFromOneId.getSurName());

                    //register
                    UserTalent userTalent = new UserTalent();
                    userTalent.setEmail(autoGeneratedEmail);
                    userTalent.setFirstName(firstName +" "+ Objects.requireNonNull(lastName,""));
                    userTalent.setLastName(lastName);
                    userTalent.setOneIdPin(userInfoFromOneId.getPin());
//                    userTalent.setProfilePhotoUrl();
                    userTalent.setIsEnabled(true);
                    UserTalent savedUserTalent = userTalentRepository.save(userTalent);
                    JwtTokenObject jwtTokenObject = JwtConfigurer.getJwtTokenObject(UserType.USER_TALENT, userTalent.getUsername(), savedUserTalent.getEmail(), Map.of());
                    var accessToken = jwtTokenObject.getToken();

                    return JwtTokenResponse
                            .builder()
                            .accessToken(accessToken)
                            .build();
                    //sign up
                }

            } else {
                String decoded = URLDecoder.decode(signInByOauthProviderDTO.getAuthorizationCode(), "UTF-8");
                String oidcToken = oauthAuthenticationService.getOIDCToken(signInByOauthProviderDTO.getEOauth2Provider(), decoded);
                String[] jwtParts = oidcToken.split("\\.");
                String payload = jwtParts[1];
                try {
                    LinkedHashMap<?, ?> claims = objectMapper.readValue(Base64.getDecoder().decode(payload.getBytes()), LinkedHashMap.class);
                    String email = (String) claims.get("email");
                    String picture = (String) claims.get("picture");
                    String firstName = (String) claims.get("given_name");
                    String lastName = (String) claims.get("family_name");
                    Optional<UserTalent> optionalUserTalent = userTalentRepository.findByEmail(email);
                    if (optionalUserTalent.isEmpty()) {
                        UserTalent userTalent = new UserTalent();
                        userTalent.setEmail(email);
                        if (lastName != null) {
                            userTalent.setFirstName(firstName + " " + lastName);
                        } else {
                            userTalent.setFirstName(firstName);
                        }
                        userTalent.setProfilePhotoUrl(picture);
                        userTalent.setIsEnabled(true);
                        UserTalent savedUserTalent = userTalentRepository.save(userTalent);
                        JwtTokenObject jwtTokenObject = JwtConfigurer.getJwtTokenObject(UserType.USER_TALENT, userTalent.getUsername(), savedUserTalent.getEmail(), Map.of());
                        var accessToken = jwtTokenObject.getToken();

                        return JwtTokenResponse
                                .builder()
                                .accessToken(accessToken)
                                .build();
                        //sign up

                    } else {

                        //sign in return jwt token
                        UserTalent userTalent = optionalUserTalent.get();
                        JwtTokenObject jwtTokenObject = JwtConfigurer.getJwtTokenObject(UserType.USER_TALENT, userTalent.getUsername(), userTalent.getEmail(), Map.of());
                        var accessToken = jwtTokenObject.getToken();

                        return JwtTokenResponse
                                .builder()
                                .accessToken(accessToken)
                                .build();


                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public UserTalent getByEmail(String email) {
        return userTalentRepository.findByEmail(email)
                .orElseThrow(GeneralApiException.throwEx("user_not_found", ErrorCodes.ERR_NOT_FOUND));
    }

    public Boolean isEmailExist(String email) {
        return userTalentRepository.existsByEmail(email);
    }


    @Transactional
    public void recoverPassword(String email) {
        log.info("recovering / resetting user password with email -> {}", email);

        var userTalent = userTalentRepository.findByEmail(email).orElseThrow(
                () -> new GeneralApiException("user_not_found", ErrorCodes.ERR_BAD_REQUEST)
        );

        userTalent.setEmailVerificationToken(UUID.randomUUID().toString());
        log.info("user found with email {} , sending email ...", email);
        Executors.newSingleThreadExecutor().submit(() -> sendResetEmail(userTalent));
    }


    public JwtTokenResponse resetPassword(String token, PasswordResetDTO data) {
        if (!data.getNewPassword().equals(data.getNewPasswordConfirmation())) {
            throw new GeneralApiException("passwords_not_match", ErrorCodes.ERR_BAD_REQUEST);
        }

        UserTalent userTalent = verifyUser(token);

        userTalent.setPassword(passwordEncoder.encode(data.getNewPassword()));
        userTalentRepository.save(userTalent);

        String accessToken = JwtConfigurer.getJwtTokenObject(UserType.USER_TALENT,
                        userTalent.getUsername(),
                        userTalent.getEmail(),
                        Map.of())
                .getToken();

        return JwtTokenResponse.builder()
                .accessToken(accessToken)
                .build();
    }


    public UserTalent verifyUser(String token) {
        var user = userTalentRepository.findByEmailVerificationToken(token).orElseThrow(
                () -> new GeneralApiException("unknown_error")
        );
        user.setEmailVerificationToken(null);
        return user;
    }


    private void sendResetEmail(UserTalent savedUser) {
        try {
            emailService.sendResetPasswordUrl(savedUser.getEmail(), Utils.generateProfileAccessUrl(
                    emailURLReset,
                    savedUser.getEmailVerificationToken()));
            log.info("Email sent to email , {}", savedUser.getEmail());
        } catch (Exception e) {
            log.error("Couldn't sent email , please check in ", e);
        }
    }

}
