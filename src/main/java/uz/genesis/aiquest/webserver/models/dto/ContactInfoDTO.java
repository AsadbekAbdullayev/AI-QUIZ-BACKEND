package uz.genesis.aiquest.webserver.models.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.genesis.aiquest.webserver.models.dto.base.BaseDTO;
import uz.genesis.aiquest.webserver.models.enums.EContactInfoType;
import uz.genesis.aiquest.webserver.utils.ErrorMessages;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContactInfoDTO extends BaseDTO<UUID> {
    @NotNull(message = "contactInfoType" + ErrorMessages.SHOULDNT_BE_NULL)
    private EContactInfoType contactInfoType;

    @NotNull(message = "contactInfoType" + ErrorMessages.SHOULDNT_BE_NULL)
    private String identifier;

    private UUID userTalentId;
}
