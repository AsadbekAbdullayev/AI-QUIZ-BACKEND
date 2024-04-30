package uz.genesis.aiquest.webserver.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import uz.genesis.aiquest.webserver.models.entity.base.BaseEntityUID;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "education_user_talent")
public class EducationUserTalent extends BaseEntityUID {

    @Column(nullable = false, name = "education_center_name", length = 1000)
    private String educationCenterName;

    @Column(nullable = false, name = "degree", length = 1000)
    private String degree;

    @Column(nullable = false, name = "direction", length = 1000)
    private String direction;

    @Column(nullable = false, name = "started_date")
    private LocalDate startedDate;

    @Column(name = "ended_date")
    private LocalDate endedDate;

    @Column(name = "definition", columnDefinition = "TEXT")
    private String definition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_talent_id", insertable = false, updatable = false)
    private UserTalent userTalent;

    @Column(name = "user_talent_id", nullable = false)
    private UUID userTalentId;

    @Column(columnDefinition = "boolean default false")
    private Boolean iCurrentlyEducateHere;

}
