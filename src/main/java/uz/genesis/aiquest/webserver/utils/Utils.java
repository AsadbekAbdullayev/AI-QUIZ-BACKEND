package uz.genesis.aiquest.webserver.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import uz.genesis.aiquest.webserver.models.dto.Header;
import uz.genesis.aiquest.webserver.component.Localization;
import uz.genesis.aiquest.webserver.models.entity.UserTalent;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Utils {


    public static void writerErrorResp(Exception exception, HttpServletResponse response, int status, String errorCode, ObjectMapper objectMapper) throws IOException {
        PrintWriter writer = response.getWriter();
        Header<String> customResponse = Header.error(errorCode, BeanUtils.getBean(Localization.class).getMessage(exception.getMessage()));
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        final var content = objectMapper.writeValueAsString(customResponse);
        writer.println(content);
    }

    public static void writerErrorResp(String message, HttpServletResponse response, int status, String errorCode, ObjectMapper objectMapper) throws IOException {
        PrintWriter writer = response.getWriter();
        Header<String> customResponse = Header.error(errorCode, message);
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        final var content = objectMapper.writeValueAsString(customResponse);
        writer.println(content);
    }

    @SafeVarargs
    public static <T> T coalesce(T... arr) {
        for (T number : arr) {
            if (Objects.nonNull(number))
                return number;
        }
        return null;
    }

    public static HttpServletRequest currentRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(requestAttributes))
            return requestAttributes.getRequest();
        throw new UnsupportedOperationException("cannot get http request, session is closed");

    }

    public static String currentRequestPath() {
        return currentRequest().getServletPath();
    }

    public static String generateProfileAccessUrl(String template, String emailVerificationToken) {
        return template.replace("TOKEN", emailVerificationToken);
    }

    public static String generateDownloadUrl(String fileServerName, String serverHost) {
        return String.format("%s/api/file/download/%s", serverHost, fileServerName);
    }


    public static UserDetails currentUser() {
        return (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public static UUID currentUserId() {
        if (currentUser() instanceof UserTalent userTalent) {
            return userTalent.getId();
        }
        return null;
    }

    public static String getLangInString() {
        return LocaleContextHolder
                .getLocale()
                .getLanguage()
                .toUpperCase();
    }

    public static String getUserType(String token) throws IOException {
        String[] parts = token.split("\\.");
        byte[] payloadInBytes = Base64.getDecoder().decode(parts[1]);
        var map = new ObjectMapper().readValue(payloadInBytes, Map.class);
        return (String) map.get(RestConstants.USER_TYPE);

    }

    public static Double proportion(Double value) {
        if (value == 0.0) {
            return 0.0;
        }
        if (value < 1) {
            return (1 / value - 1) * -100;
        } else return (value - 1) * 100;
    }

    public static String extractUsername(String linkedinProfileURL) {

        if (linkedinProfileURL.endsWith("/")) {
            linkedinProfileURL = linkedinProfileURL.substring(0, linkedinProfileURL.length() - 1);
        }

        return linkedinProfileURL.substring(linkedinProfileURL.lastIndexOf("/") + 1);
    }

    public static String generateEmail(String firstName, String midName) {
        return firstName.toLowerCase() + "_" + midName.toLowerCase() + "@aiquest.uz";
    }

    public static String generatePINFL() {
        int[] start = {31, 32, 52, 51, 61};
        StringBuilder pinfl = new StringBuilder();
        pinfl.append(start[new Random().nextInt(start.length )]);
        String randomNumbers = IntStream.range(0, 12)
                .mapToObj(value -> Integer.toString(new Random().nextInt(9)))
                .collect(Collectors.joining());
        return pinfl.append(randomNumbers)
                .toString();

    }
}
