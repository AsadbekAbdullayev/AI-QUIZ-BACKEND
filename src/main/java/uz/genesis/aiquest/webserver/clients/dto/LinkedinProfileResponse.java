package uz.genesis.aiquest.webserver.clients.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LinkedinProfileResponse {
    private String firstName;
    private String lastName;
    private Boolean isOpenToWork;
    private Boolean isHiring;
    private String profilePicture;
    private String summary;
    private String headline;
    private Geolocation geo;
    private Object languages;
    private List<Education> educations;
    @JsonProperty(value = "position")
    private List<Position> positions;
    private List<Skill> skills;
    private List<Certification> certifications;
}