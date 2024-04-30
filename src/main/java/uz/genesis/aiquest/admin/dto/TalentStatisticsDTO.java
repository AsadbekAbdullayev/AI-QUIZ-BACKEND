package uz.genesis.aiquest.admin.dto;

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
public class TalentStatisticsDTO extends BaseDTO<UUID> {
    @NotNull(message = "content" + ErrorMessages.SHOULDNT_BE_NULL)
    private String content;
    @NotNull(message = "count" + ErrorMessages.SHOULDNT_BE_NULL)
    private Integer count;
}
