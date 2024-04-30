package uz.genesis.aiquest.webserver.models.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.genesis.aiquest.webserver.utils.ErrorMessages;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AnswerTheQuestionDTO {
    @NotNull(message = "testId" + ErrorMessages.SHOULDNT_BE_NULL)
    private UUID testId;
    @NotEmpty(message = "questionAnswers" + ErrorMessages.SHOULDNT_BE_EMPTY)
    private List<@Valid QuestionAnswerDTO> questionAnswers;

    public Integer getSelectedAnswersCount() {
        if (questionAnswers == null || (questionAnswers.isEmpty())) {
            return 0;
        }
        return (int) questionAnswers.stream().filter(QuestionAnswerDTO::getIsSelected).count();
    }
}
