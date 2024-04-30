package uz.genesis.aiquest.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.genesis.aiquest.webserver.models.enums.TestType;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ResultOfTalentDTO {
    private String talentName;
    private String talentEmail;
    private Boolean isVerified;
    private Integer finalScore;
    private String subDirectionCaption;
    private TestType testType;
    private Long duration;
}
