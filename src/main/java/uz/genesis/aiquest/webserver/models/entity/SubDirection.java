package uz.genesis.aiquest.webserver.models.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.genesis.aiquest.webserver.models.entity.base.CommonFields;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sub_directions")
public class SubDirection extends CommonFields {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "direction_id")
    private Direction direction;

    @OneToOne(mappedBy = "subDirection", fetch = FetchType.EAGER)
    private DirectionTest directionTest;
}
