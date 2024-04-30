package uz.genesis.aiquest.webserver.models.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.genesis.aiquest.webserver.models.dto.base.BaseDTO;
import uz.genesis.aiquest.webserver.utils.ErrorMessages;
import uz.genesis.aiquest.webserver.models.enums.ELanguageLevel;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserLangLevelDTO extends BaseDTO<Long> {
    @NotNull(message = "languageId" + ErrorMessages.SHOULDNT_BE_NULL)
    private Long languageId;

    //optional
    private String languageCertificate;

    @NotNull(message = "level"  + ErrorMessages.SHOULDNT_BE_NULL)
    private ELanguageLevel level;

    private String langLevelCaption;





}
