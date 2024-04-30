package uz.genesis.aiquest.webserver.models.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.genesis.aiquest.webserver.models.dto.base.BaseUUIDTO;
import uz.genesis.aiquest.webserver.models.enums.MeetingType;
import uz.genesis.aiquest.webserver.models.enums.ZoomRequestStatus;
import uz.genesis.aiquest.webserver.utils.ErrorMessages;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ZoomRequestDTO extends BaseUUIDTO {

    private UUID userTalentId;

    @NotNull(message = "meetingType " + ErrorMessages.SHOULDNT_BE_NULL)
    private MeetingType meetingType;

    @NotNull(message = "meetingTime " + ErrorMessages.SHOULDNT_BE_NULL)
    private LocalDateTime meetingTime;

    @Size(max = 2000)
    private String requestInfo;

    private String decliningReason;

    @Size(max = 1000)
    private String zoomLink;

    private ZoomRequestStatus status;

    private UserTalentDTO userTalent;
}
