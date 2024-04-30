package uz.genesis.aiquest.admin.dto.projection;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ConductedTestProjection {
    LocalDateTime getCreatedAt();

    UUID getUserTalentId();

    Double getFinalScore();

    LocalDateTime getFinishedTime();

    LocalDateTime getStartedTime();

    String getTestType();

    LocalDateTime getSubmittedTime();

    @Value(value = "#{@subDirectionRepository.getSubdirectionCaption(target.subdirectionId)}")
    String getSubdirection();

    String getEmail();
}