package uz.genesis.aiquest.admin.dto.projection;

import java.sql.Timestamp;
import java.util.UUID;

public interface BlogProjection {
    UUID getId();
    String getTitle();
    String getTitleRu();
    String getTitleEn();
    String getContent();
    String getContentEn();
    String getContentRu();
    String getBlogPhotoUrl();
    Boolean getIsActive();
    String getFileOriginalName();

    Long getFileSize();
    String getFileContentType();
    String getFileServerName();
    String getFileLocation();
    UUID getAttachmentId();
    Long getCount();

    Timestamp getCreatedAt();
}
