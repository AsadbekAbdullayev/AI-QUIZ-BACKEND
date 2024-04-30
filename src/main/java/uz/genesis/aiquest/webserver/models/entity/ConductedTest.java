package uz.genesis.aiquest.webserver.models.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.genesis.aiquest.webserver.models.entity.base.BaseEntityUID;
import uz.genesis.aiquest.webserver.models.enums.TestType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "conducted_tests")
@Builder
public class ConductedTest extends BaseEntityUID {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_talent_id")
    private UserTalent userTalent;

    @Column(name = "start_date_time")
    private LocalDateTime startDateTime;

    @Column(name = "finish_date_time")
    private LocalDateTime finishDateTime;

    @Column(name = "submitted_time")
    private LocalDateTime submittedTime;

    @Enumerated(value = EnumType.STRING)
    private TestType testType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_direction_id", insertable = false, updatable = false)
    private SubDirection subDirection;

    @Column(name = "sub_direction_id", nullable = false)
    private Long subDirectionId;

    @Column(name = "is_verified")
    private Boolean isVerified; //when the test finishes we should calculate results and update this column if user talent can exceed min vericiation value

    private Integer finalScore;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "conductedTest")
    private List<GeneratedTest> generatedTests;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "conductedTest")
    private List<AiQuestion> aiQuestionnaries;


    public Long getRemainedTimeInMilliseconds() {
        return Duration.between(LocalDateTime.now(), finishDateTime)
                .getSeconds() * 1000;
    }

}
