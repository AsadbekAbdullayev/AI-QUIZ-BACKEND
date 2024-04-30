package uz.genesis.aiquest.webserver.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GeneratedTestDTO {

    private QuestionDTO question;

    private List<QuestionAnswerDTO> questionAnswers;

    private Integer questionIndex;

    private Boolean answered;

    private Boolean isCorrectlyAnswered;

}
