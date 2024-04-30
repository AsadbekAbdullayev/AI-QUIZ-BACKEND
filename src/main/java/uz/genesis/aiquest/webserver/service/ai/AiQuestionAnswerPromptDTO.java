package uz.genesis.aiquest.webserver.service.ai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AiQuestionAnswerPromptDTO {
    private String question;
    private String answer;
}
