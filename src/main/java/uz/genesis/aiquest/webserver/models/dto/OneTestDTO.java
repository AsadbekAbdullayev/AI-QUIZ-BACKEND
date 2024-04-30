package uz.genesis.aiquest.webserver.models.dto;

import lombok.*;
import uz.genesis.aiquest.webserver.models.enums.QuestionType;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OneTestDTO {
    private UUID testId;
    private String questionCaption;
    private QuestionType questionType;
    private String codeExample;
    private List<QuestionAnswerDTO> chosenAnswers;
    private Integer questionIndex;
    private Long remainedTime;
}
