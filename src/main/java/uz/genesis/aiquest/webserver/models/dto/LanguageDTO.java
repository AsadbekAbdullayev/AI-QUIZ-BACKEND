package uz.genesis.aiquest.webserver.models.dto;

import lombok.*;
import uz.genesis.aiquest.webserver.models.dto.base.BaseDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LanguageDTO extends BaseDTO<Long> {
    private Long id;
    private String caption;
    private String langCode;
}
