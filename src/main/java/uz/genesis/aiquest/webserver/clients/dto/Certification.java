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
public class Certification {
    private String name;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate start;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate end;

    private String authority;

    private Company company;
    private TimePeriod timePeriod;
}