package uz.genesis.aiquest.webserver.models.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.genesis.aiquest.webserver.models.dto.base.BaseUUIDTO;
import uz.genesis.aiquest.webserver.models.enums.EGenderType;
import uz.genesis.aiquest.webserver.utils.ErrorMessages;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserTalentDTO extends BaseUUIDTO {
    @NotBlank(message = "firstName " + ErrorMessages.SHOULDNT_BE_EMPTY)
    private String firstName;
//    @NotBlank(message = "lastName " + ErrorMessages.SHOULDNT_BE_EMPTY)
    private String lastName;

//    @NotNull(message = "birthDate " + ErrorMessages.SHOULDNT_BE_NULL)
    private LocalDate birthDate;

//    @NotNull(message = "regionId " + ErrorMessages.SHOULDNT_BE_NULL)
    private Long regionId;

//    @NotNull(message = "phoneNumber" + ErrorMessages.SHOULDNT_BE_NULL)
    private String phoneNumber;

//    @NotNull(message = "genderType" + ErrorMessages.SHOULDNT_BE_NULL)
    private EGenderType EGenderType;

//    @NotNull(message = "subDirectionId " + ErrorMessages.SHOULDNT_BE_NULL)
    private Long subDirectionId;

    private String subDirectionCaption;

    private String directionCaption;

    private String profilePhotoURL;

    private Boolean hasTicket;

    @NotNull(message = "email " + ErrorMessages.SHOULDNT_BE_NULL)
    @Email
    private String email;

    @NotNull(message = "password " + ErrorMessages.SHOULDNT_BE_NULL)
//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    private String password;

//    @NotNull(message = "copyPassword " + ErrorMessages.SHOULDNT_BE_NULL)
    private String copyPassword;


}
