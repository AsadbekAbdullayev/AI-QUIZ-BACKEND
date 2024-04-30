package uz.genesis.aiquest.webserver.models.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.genesis.aiquest.webserver.models.dto.base.BaseSequentialIdDTO;
import uz.genesis.aiquest.webserver.utils.ErrorMessages;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class DirectionDTO extends BaseSequentialIdDTO {
    @NotNull(message = "caption " + ErrorMessages.SHOULDNT_BE_NULL)
    private String caption;

    private Boolean isActive;

    @NotNull
    @NotEmpty
    public List<SubDirectionDTO> subDirections;
}
