package uz.genesis.aiquest.webserver.models.dto;

import lombok.*;
import uz.genesis.aiquest.webserver.models.enums.TestType;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TestResultDTO  {
    private Integer totalQuestions;
    private Integer correctAnswers;
    private LocalDateTime submittedTime;
    private Boolean isVerified;
    private TestType testType;
}
