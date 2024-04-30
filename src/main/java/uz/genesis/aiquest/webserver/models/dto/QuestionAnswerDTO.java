package uz.genesis.aiquest.webserver.models.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import uz.genesis.aiquest.webserver.utils.ErrorMessages;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class QuestionAnswerDTO {

    @NotNull(message = "id" + ErrorMessages.SHOULDNT_BE_NULL)
    private UUID id;

    private String optionCaption;

    @NotNull(message = "isSelected" + ErrorMessages.SHOULDNT_BE_NULL)
    private Boolean isSelected;
}
