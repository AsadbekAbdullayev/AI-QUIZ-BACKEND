package uz.genesis.aiquest.webserver.models.dto.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OneIdUserInfoResponseDTO {
    @JsonProperty(value = "sur_name")
    private String surName;

    @JsonProperty(value = "pin")
    private String pin;

    @JsonProperty(value = "user_id")
    private String userId;

    @JsonProperty(value = "mid_name")
    private String midName;

    @JsonProperty(value = "valid")
    private boolean valid;

    @JsonProperty(value = "user_type")
    private String userType;

    @JsonProperty(value = "sess_id")
    private String sessionId;

    @JsonProperty(value = "ret_cd")
    private String retCd;

    @JsonProperty("auth_method")
    private String authMethod;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("full_name")
    private String fullName;
}
