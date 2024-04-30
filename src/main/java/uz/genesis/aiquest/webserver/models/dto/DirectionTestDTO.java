package uz.genesis.aiquest.webserver.models.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
public class DirectionTestDTO extends BaseSequentialIdDTO {

    @NotNull(message = "timerMinutesForStandardTest " + ErrorMessages.SHOULDNT_BE_NULL)
    @Min(value = 10)
    private Integer timerMinutesForStandardTest;

    @NotNull(message = "timerMinutesForDetailedTest " + ErrorMessages.SHOULDNT_BE_NULL)
    @Min(value = 10)
    private Integer timerMinutesForDetailedTest;

    @NotNull(message = "standardTestQuantity " + ErrorMessages.SHOULDNT_BE_NULL)
    @Min(value = 10)
    private Integer standardTestQuantity;

    @NotNull(message = "detailedTestQuantity " + ErrorMessages.SHOULDNT_BE_NULL)
    @Min(value = 10)
    private Integer detailedTestQuantity;

    @NotNull(message = "subDirectionId " + ErrorMessages.SHOULDNT_BE_NULL)
    private Long subDirectionId;

    private String subDirectionCaption;

    private Integer totalQuestionQuantity;

}
