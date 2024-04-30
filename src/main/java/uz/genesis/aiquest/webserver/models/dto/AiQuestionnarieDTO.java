package uz.genesis.aiquest.webserver.models.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AiQuestionnarieDTO {
    private UUID id;

    private String aiQuestion;

    private String userAnswer;



}
