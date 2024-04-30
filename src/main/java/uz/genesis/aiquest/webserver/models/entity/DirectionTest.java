package uz.genesis.aiquest.webserver.models.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import uz.genesis.aiquest.webserver.commons.annotations.SearchableField;
import uz.genesis.aiquest.webserver.models.entity.base.BaseEntitySequentialID;
import uz.genesis.aiquest.webserver.models.enums.TestType;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "direction_tests")
public class DirectionTest extends BaseEntitySequentialID {

    @SearchableField(innerSearField = "caption")
    @OneToOne
    @JoinColumn(name = "sub_direction_id", insertable = false, updatable = false)
    private SubDirection subDirection;

    @Column(name = "sub_direction_id", nullable = false, unique = true)
    private Long subDirectionId;

    @Column(nullable = false)
    private Integer timerMinutesForStandardTest;

    @Column(nullable = false)
    private Integer timerMinutesForDetailedTest;

    @Column(nullable = false)
    private Integer standardTestQuantity;

    @Column(nullable = false)
    private Integer detailedTestQuantity;

    public int getTestQuantityByType(TestType testType) {
        if (testType.equals(TestType.DETAILED_TEST)) {
            return detailedTestQuantity;
        }else return standardTestQuantity;
    }


    public LocalDateTime getFinishDateTime(TestType testType) {
        if (testType.equals(TestType.STANDARD_TEST))
            return LocalDateTime.now().plusMinutes(timerMinutesForStandardTest);
        else return LocalDateTime.now().plusMinutes(timerMinutesForDetailedTest);
    }
}
