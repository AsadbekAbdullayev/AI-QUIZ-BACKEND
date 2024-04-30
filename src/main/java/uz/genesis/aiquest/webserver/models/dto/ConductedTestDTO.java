package uz.genesis.aiquest.webserver.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.genesis.aiquest.webserver.models.enums.TestType;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConductedTestDTO {

    private LocalDateTime startDateTime;

    private LocalDateTime finishDateTime;

    private LocalDateTime submittedTime;

    private TestType testType;

    private String subDirectionCaption;

    private Long subDirectionId;

    private Boolean isVerified; //when the test finishes we should calculate results and update this column if user talent can exceed min vericiation value

    private Integer finalScore;

    private List<GeneratedTestDTO> generatedTests;

}
