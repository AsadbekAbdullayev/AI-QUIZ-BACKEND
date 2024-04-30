package uz.genesis.aiquest.webserver.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import uz.genesis.aiquest.webserver.models.enums.QuestionValidationState;
import uz.genesis.aiquest.webserver.models.entity.base.BaseEntityUID;
import uz.genesis.aiquest.webserver.models.enums.QuestionLanguage;
import uz.genesis.aiquest.webserver.models.enums.QuestionType;
import uz.genesis.aiquest.webserver.models.enums.TestType;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questions")
public class Question extends BaseEntityUID {
    @Column(nullable = false, columnDefinition = "TEXT")
    private String questionCaption;

    @Column(columnDefinition = "TEXT")
    private String codeExample;

//    @Enumerated(value = EnumType.STRING)
//    @Column(name = "question_level", nullable = false)
//    private EQuestionLevel questionLevel;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "question_validation_state", columnDefinition = "varchar(255) default 'VALID'")
    private QuestionValidationState questionValidationState = QuestionValidationState.VALID;

    @Column(name = "ai_feedback", columnDefinition = "TEXT")
    private String aiFeedback;

    @Enumerated(EnumType.STRING)
    private TestType forTestType; //this question is belong to which test type

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question", fetch = FetchType.LAZY)
    private List<QuestionOption> questionOptions;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "question_type", nullable = false)
    private QuestionType questionType;

    @ManyToOne
    @JoinColumn(name = "sub_direction_id", insertable = false, updatable = false)
    private SubDirection direction;

    @Column(name = "sub_direction_id", nullable = false)
    private Long directionId;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private QuestionLanguage questionLanguage;

    @Override
    public String toString() {
        return "Question{" +
               "questionCaption='" + questionCaption + '\'' +
               ", codeExample='" + codeExample + '\'' +
               ", questionValidationState=" + questionValidationState +
               ", aiFeedback='" + aiFeedback + '\'' +
               ", forTestType=" + forTestType +
               ", questionType=" + questionType +
               ", direction=" + direction +
               ", directionId=" + directionId +
               ", questionLanguage=" + questionLanguage +
               '}';
    }
}
