package uz.genesis.aiquest.admin.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.genesis.aiquest.webserver.models.dto.base.BaseDTO;

import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AdminUserTalentDTO extends BaseDTO<UUID> {

    private String firstName;

    private String lastName;

    private String profilePhotoUrl;

    private Timestamp createdAt;

    private String directionCaption;

    private String subDirectionCaption;

    private String email;

    private String phoneNumber;

    private Boolean isStandardTestPassed;

    private Boolean isDetailedTestPassed;

    private String oneIdPin;
}
