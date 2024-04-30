package uz.genesis.aiquest.webserver.models.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CalendarDateDTO {
    private LocalDate date;
    @Builder.Default
    private Boolean isSelectable = Boolean.FALSE;
}
