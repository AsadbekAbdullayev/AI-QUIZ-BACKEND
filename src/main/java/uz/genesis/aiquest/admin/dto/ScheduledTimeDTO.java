package uz.genesis.aiquest.admin.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.genesis.aiquest.webserver.models.dto.base.BaseSequentialIdDTO;

import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ScheduledTimeDTO extends BaseSequentialIdDTO {
    private Boolean isMondayActive = Boolean.FALSE;
    private LocalTime mondayStartTime;
    private LocalTime mondayEndTime;

    private Boolean isTuesdayActive = Boolean.FALSE;
    private LocalTime tuesdayStartTime;
    private LocalTime tuesdayEndTime;

    private Boolean isWednesdayActive = Boolean.FALSE;
    private LocalTime wednesdayStartTime;
    private LocalTime wednesdayEndTime;

    private Boolean isThursdayActive = Boolean.FALSE;
    private LocalTime thursdayStartTime;
    private LocalTime thursdayEndTime;

    private Boolean isFridayActive = Boolean.FALSE;
    private LocalTime fridayStartTime;
    private LocalTime fridayEndTime;

    private Boolean isSaturdayActive = Boolean.FALSE;
    private LocalTime saturdayStartTime;
    private LocalTime saturdayEndTime;

    private Boolean isSundayActive = Boolean.FALSE;
    private LocalTime sundayStartTime;
    private LocalTime sundayEndTime;

    private List<@Valid ScheduleTimeHolidayDTO> holidays;
}
