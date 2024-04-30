package uz.genesis.aiquest.webserver.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TalentLinkedinUrlDTO {

    @NotBlank(message = "Linkedin profile url shouldnt be blank")
    @Pattern(regexp = "http(s)?:\\/\\/([\\w]+\\.)?linkedin\\.com\\/in\\/[A-z0-9_-]+\\/?", message = "not valid url")
    private String linkedinProfileUrl;
}
