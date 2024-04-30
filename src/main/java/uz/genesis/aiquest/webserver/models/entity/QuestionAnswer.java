package uz.genesis.aiquest.webserver.models.entity;


import jakarta.persistence.*;
import lombok.*;
import uz.genesis.aiquest.webserver.models.entity.base.BaseEntityUID;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "question_answers")
public class QuestionAnswer extends BaseEntityUID {

    @ManyToOne
    @JoinColumn(name = "question_option_id")
    private QuestionOption questionOption;

    @Column(name = "is_selected")
    private Boolean isSelected;

    @ManyToOne(optional = false)
    private GeneratedTest generatedTest;
}
