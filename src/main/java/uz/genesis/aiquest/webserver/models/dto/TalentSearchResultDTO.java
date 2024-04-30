package uz.genesis.aiquest.webserver.models.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TalentSearchResultDTO {
    private String firstName;
    private String lastName;
    private String directionCaption;
    private Integer detailedTestScore;
    private String regionCaption;
    private Integer minSalary;
    private Integer maxSalary;
    private Boolean hasTicket;
}
