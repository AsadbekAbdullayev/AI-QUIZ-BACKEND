package uz.genesis.aiquest.webserver.models.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class CommonFields extends BaseEntitySequentialID{

    @Column(name = "caption", nullable = false)
    private String caption;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = Boolean.TRUE;
}
