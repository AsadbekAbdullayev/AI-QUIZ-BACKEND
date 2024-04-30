package uz.genesis.aiquest.webserver.models.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import uz.genesis.aiquest.webserver.models.enums.QuestionLanguage;
import uz.genesis.aiquest.webserver.models.enums.TestType;
import uz.genesis.aiquest.webserver.utils.ErrorMessages;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddQuestionDTO {
    @NotNull
    private Long directionId;

    @NotNull(message = "testType " + ErrorMessages.SHOULDNT_BE_NULL)
    private TestType testType;

    @NotNull
    private QuestionLanguage questionLanguage;

    @NotEmpty
    private List<@Valid QuestionDTO> questions;
}
