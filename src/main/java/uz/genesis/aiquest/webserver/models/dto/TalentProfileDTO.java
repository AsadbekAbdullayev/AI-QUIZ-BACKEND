package uz.genesis.aiquest.webserver.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TalentProfileDTO {
    private UUID id;
    private String phoneNumber;
    private String email;
    private String firstName;
    private String lastName;
    private String genderType;
    private Long regionId;

    private String placeOfStudy;
    private Long degreeId;
    private String degreeCaption;
    private LocalDate birthDate;
    private String regionName;
    private Integer minSalary;
    private String profilePhotoUrl;
    private Integer standardTestScore;
    private Integer detailedTestScore;
    private Boolean hasTicket;
    private Boolean isStandardTestPassed;
    private String subDirectionCaption;
    private Long directionId;
    private String directionCaption;
    private Long subDirectionId;
    private Integer verificationTestScore;
    private String oneIdPin;
//test

    public void nullifyNotUpdatableFields() {
        verificationTestScore = null;
        hasTicket = null;
    }
}
