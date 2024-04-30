package uz.genesis.aiquest.webserver.clients.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.genesis.aiquest.webserver.deserializers.LocalDateDeserializer;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Position {
    private String companyName;

    private String companyUsername;

    private String companyURL;

    private String companyIndustry;

    private String companyStaffCountRange;

    private String title;

    private String location;

    private String description;

    private String employmentType;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate start;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate end;
}