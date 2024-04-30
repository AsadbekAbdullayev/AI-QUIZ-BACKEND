package uz.genesis.aiquest.webserver.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.genesis.aiquest.webserver.models.dto.base.BaseDTO;
import uz.genesis.aiquest.webserver.utils.ErrorMessages;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuestionOptionDTO extends BaseDTO<UUID> {

    @NotBlank(message = "optionCaption" + ErrorMessages.SHOULDNT_BE_EMPTY)
    private String optionCaption;

    @NotNull(message = "isCorrect" + ErrorMessages.SHOULDNT_BE_NULL)
    private Boolean isCorrect;
}
