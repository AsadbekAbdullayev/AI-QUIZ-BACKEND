package uz.genesis.aiquest.webserver.models.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.genesis.aiquest.webserver.models.entity.base.BaseEntityUID;

@Getter
@Setter

@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "question_options")
public class QuestionOption extends BaseEntityUID {

    @Column(name = "option_caption", nullable = false, length = 1024)
    private String optionCaption;

    @Column(name = "is_correct", nullable = false)
    private Boolean isCorrect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

}
