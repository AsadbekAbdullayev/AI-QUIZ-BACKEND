package uz.genesis.aiquest.webserver.models.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.genesis.aiquest.webserver.models.entity.base.BaseEntityUID;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "generated_tests")
public class GeneratedTest extends BaseEntityUID {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_talent_id")
    private UserTalent userTalent;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,  mappedBy = "generatedTest")
    private List<QuestionAnswer> questionAnswers;

    @Column(name = "question_index", nullable = false)
    private Integer questionIndex;

    private Boolean answered;

    @Column(name = "is_correctly_answered")
    private Boolean isCorrectlyAnswered; //default should be null , if this column is null in that case talent have not answered yet

    @ManyToOne(fetch = FetchType.EAGER)
    private ConductedTest conductedTest;


}
