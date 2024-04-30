package uz.genesis.aiquest.webserver.models.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.genesis.aiquest.webserver.models.entity.base.BaseEntityUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "ai_questions")
public class AiQuestion extends BaseEntityUID {

    @Column(name = "ai_question", columnDefinition = "TEXT")
    private String aiQuestion;

    @Column(name = "user_answer", columnDefinition = "TEXT")
    private String userAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_talent_id")
    private UserTalent userTalent;

    @Column(name = "question_index", nullable = false)
    private Integer questionIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    private ConductedTest conductedTest;

    @ManyToOne(fetch = FetchType.LAZY)
    private DirectionTopic directionTopic;

    public String toString() {
        return "{" +
                "\"question\": \"" + aiQuestion + "\"" +
                ", \"answer\": \"" + userAnswer + "\"" +
                '}';
    }

}
