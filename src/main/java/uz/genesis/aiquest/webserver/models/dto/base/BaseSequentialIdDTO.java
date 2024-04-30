package uz.genesis.aiquest.webserver.models.dto.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseSequentialIdDTO extends BaseDTO<Long> {
    private Long id;
}
