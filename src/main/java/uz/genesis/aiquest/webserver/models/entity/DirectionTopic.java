package uz.genesis.aiquest.webserver.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import uz.genesis.aiquest.webserver.models.entity.base.BaseEntitySequentialID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "direction_topics")
public class DirectionTopic extends BaseEntitySequentialID {
    private String topicName;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private SubDirection subDirection;
}
