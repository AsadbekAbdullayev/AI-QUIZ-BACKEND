package uz.genesis.aiquest.webserver.models.dto;

import jakarta.persistence.Column;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class ExperienceDTO {
    @Column(nullable = false, name = "position")
    private String position;
    private String employer;
    private String city;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isPresent;
    private String description;
    private UUID userTalendId;
}
