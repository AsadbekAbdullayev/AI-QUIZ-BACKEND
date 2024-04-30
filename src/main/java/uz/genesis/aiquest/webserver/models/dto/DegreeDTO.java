package uz.genesis.aiquest.webserver.models.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.genesis.aiquest.webserver.models.dto.base.BaseSequentialIdDTO;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class DegreeDTO extends BaseSequentialIdDTO {

    @Column(name = "caption", nullable = false)
    private String caption;
}
