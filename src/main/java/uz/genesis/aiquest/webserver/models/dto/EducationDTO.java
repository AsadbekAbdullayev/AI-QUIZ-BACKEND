package uz.genesis.aiquest.webserver.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.genesis.aiquest.webserver.models.dto.base.BaseDTO;
import uz.genesis.aiquest.webserver.utils.ErrorMessages;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EducationDTO extends BaseDTO<UUID> {

    @NotNull(message = "educationCenterName" + ErrorMessages.SHOULDNT_BE_NULL)
    @Size( max = 2000)
    private String educationCenterName;

    @NotNull(message = "degree" + ErrorMessages.SHOULDNT_BE_NULL)
    @Size(max = 2000)
    private String degree;

    @NotNull(message = "direction" + ErrorMessages.SHOULDNT_BE_NULL)
    @Size( max = 2000)
    private String direction;

    @NotNull(message = "startedDate" + ErrorMessages.SHOULDNT_BE_NULL)
    private LocalDate startedDate;

    private LocalDate endedDate;

    @Size(max = 2000)
    private String definition;

    private UUID userTalentId;

    @JsonProperty(value = "iCurrentlyEducateHere")
    private Boolean iCurrentlyEducateHere = false;

    @AssertTrue(message = "isEndDateOrStartDateNotNull should be true")
    public boolean isEndDateOrStartDateNotNull() {
        return Objects.nonNull(endedDate) && !iCurrentlyEducateHere ||
                Objects.nonNull(iCurrentlyEducateHere) && iCurrentlyEducateHere && Objects.isNull(endedDate);
    }
    @AssertTrue(message = "startDate shouldn't be in the future")
    public boolean isStartDateNotInFuture() {
        return Objects.nonNull(startedDate) && startedDate.isBefore(LocalDate.now());
    }
}
