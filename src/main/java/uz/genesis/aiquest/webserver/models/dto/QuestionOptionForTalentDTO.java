package uz.genesis.aiquest.webserver.models.dto;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class QuestionOptionForTalentDTO {
    private UUID id;
    private String optionCaption;


}
