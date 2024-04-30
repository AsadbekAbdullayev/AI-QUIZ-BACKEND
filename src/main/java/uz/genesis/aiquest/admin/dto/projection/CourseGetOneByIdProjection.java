package uz.genesis.aiquest.admin.dto.projection;

import org.springframework.beans.factory.annotation.Value;

public interface CourseGetOneByIdProjection {
    Long getId();
    String getCourseName();

    String getCourseOwner();

    String getRedirectUrl();
    @Value(value = "#{@directionRepository.findById_For_Projection(target.id)}")
    CourseDirectionProjection getDirection();
    Boolean getIsActive();
    String getDescription();

    @Value(value = "#{@attachmentRepository.findById_For_Projection(target.id)}")
    AttachmentProjection getAttachment();
}
