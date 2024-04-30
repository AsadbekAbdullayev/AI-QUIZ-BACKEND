package uz.genesis.aiquest.webserver.models.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.genesis.aiquest.webserver.models.dto.base.BaseUUIDTO;
import uz.genesis.aiquest.webserver.models.enums.QuestionLanguage;
import uz.genesis.aiquest.webserver.models.enums.QuestionType;
import uz.genesis.aiquest.webserver.models.enums.QuestionValidationState;
import uz.genesis.aiquest.webserver.models.enums.TestType;
import uz.genesis.aiquest.webserver.utils.ErrorMessages;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuestionDTO extends BaseUUIDTO {

    @NotBlank(message = "questionCaption" + ErrorMessages.SHOULDNT_BE_EMPTY)
    private String questionCaption;

    private String codeExample;

    private QuestionType questionType;

    private Long directionId;

    private QuestionLanguage questionLanguage;

    private TestType forTestType;

    private String aiFeedback;

    private QuestionValidationState questionValidationState;

    @NotEmpty
    private List<@Valid QuestionOptionDTO> questionOptions;
}
