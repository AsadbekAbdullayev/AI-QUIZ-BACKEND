package uz.genesis.aiquest.webserver.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.genesis.aiquest.webserver.models.enums.TestType;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AiQuestResultDTO {
    private TestType testType = TestType.AI_QUESTIONARIO;
    private String aiRecommendation;
    private String conclusion;
    private String seniorityLevel;
    private Integer resultInPercentage;
    private Integer totalQuestions;
    private LocalDateTime submittedTime;
}
