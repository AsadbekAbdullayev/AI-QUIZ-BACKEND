package uz.genesis.aiquest.webserver.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.genesis.aiquest.webserver.models.dto.base.BaseSequentialIdDTO;
import uz.genesis.aiquest.webserver.utils.ErrorMessages;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class SubDirectionDTO extends BaseSequentialIdDTO {

    @NotBlank(message = "caption" + ErrorMessages.SHOULDNT_BE_EMPTY)
    private String caption;

    private Boolean isTestAdded;

}
