package uz.genesis.aiquest.webserver.models.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.genesis.aiquest.webserver.models.entity.base.CommonFields;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "directions")
public class Direction extends CommonFields {
    @Column(nullable = false)
    private String caption;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true,mappedBy = "direction")
    private List<SubDirection> subDirections;

}
