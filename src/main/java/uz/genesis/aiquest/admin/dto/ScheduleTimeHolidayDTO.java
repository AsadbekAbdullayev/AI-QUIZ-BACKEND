package uz.genesis.aiquest.admin.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.genesis.aiquest.webserver.models.dto.base.BaseSequentialIdDTO;
import uz.genesis.aiquest.webserver.utils.ErrorMessages;

import java.time.LocalDate;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScheduleTimeHolidayDTO extends BaseSequentialIdDTO {

    @NotNull(message = "holidayName" + ErrorMessages.SHOULDNT_BE_NULL)
    private String holidayName;

    @NotNull(message = "holidayStartDate" + ErrorMessages.SHOULDNT_BE_NULL)
    private LocalDate holidayStartDate;

    private LocalDate holidayEndDate;

    @AssertTrue(message = "startDate should be before end date")
    public boolean isEndDateBeforeStartDate() {
        if (Objects.isNull(holidayEndDate))
            return true;
        return holidayStartDate.isBefore(holidayEndDate) || holidayStartDate.isEqual(holidayEndDate);
    }

}
