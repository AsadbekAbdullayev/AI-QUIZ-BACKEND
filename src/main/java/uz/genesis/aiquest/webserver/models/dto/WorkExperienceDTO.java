package uz.genesis.aiquest.webserver.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.i18n.LocaleContextHolder;
import uz.genesis.aiquest.webserver.models.dto.base.BaseDTO;
import uz.genesis.aiquest.webserver.utils.ErrorMessages;
import uz.genesis.aiquest.webserver.models.enums.EmploymentType;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.TextStyle;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WorkExperienceDTO extends BaseDTO<UUID> {

    @NotNull(message = "companyName" + ErrorMessages.SHOULDNT_BE_NULL)
    private String companyName;

    @NotNull(message = "position" + ErrorMessages.SHOULDNT_BE_NULL)
    private String position;

    private String companyWebSite;

    @NotNull(message = "employmentType" + ErrorMessages.SHOULDNT_BE_NULL)
    private EmploymentType employmentType;

    @NotNull(message = "startDate" + ErrorMessages.SHOULDNT_BE_NULL)
    private LocalDate startDate;

    private String employmentTypeCaption;
    //optional
    private LocalDate endDate;

    @JsonProperty(value = "iCurrentlyWorkHere")
    private Boolean iCurrentlyWorkHere;

    private UUID userTalendId;

    private String companyLocation;

    private String description;

    public Integer getDurationYears() {
        var period = Period.between(startDate, Objects.isNull(endDate) ? LocalDate.now() : endDate);
        return period.getYears();
    }

    public Integer getDurationMonths() {
        var period = Period.between(startDate, Objects.isNull(endDate) ? LocalDate.now() : endDate);
        return period.getMonths();
    }
    public String getStartMonth() {
        return startDate.getMonth().getDisplayName(TextStyle.FULL, LocaleContextHolder.getLocale());
    }

    public Integer getStartYear() {
        return startDate.getYear();
    }

    public String getEndMonth() {
        if (Objects.isNull(endDate))
            return null;

        return endDate.getMonth().getDisplayName(TextStyle.FULL, LocaleContextHolder.getLocale());
    }

    public Integer getEndYear() {
        if (Objects.isNull(endDate)) {
            return null;
        }
        return endDate.getYear();
    }

    @AssertTrue(message = "isEndDateOrStartDateNotNull should be true")
    public boolean isEndDateOrStartDateNotNull() {
        return Objects.nonNull(endDate) && !iCurrentlyWorkHere ||
                Objects.nonNull(iCurrentlyWorkHere) && iCurrentlyWorkHere && Objects.isNull(endDate);
    }

    @AssertTrue(message = "startDate shouldn't be in the future")
    public boolean isStartDateNotInFuture() {
        return Objects.nonNull(startDate) && startDate.isBefore(LocalDate.now());
    }
}
