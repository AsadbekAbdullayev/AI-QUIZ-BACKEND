package uz.genesis.aiquest.webserver.models.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ConductedTestCreateDTO {
    private UUID conductedTestId;
    private Long remainedTime;
    private List<Integer> questionIndexes;

}
