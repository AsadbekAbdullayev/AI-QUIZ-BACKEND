package uz.genesis.aiquest.admin.dto.projection;

public interface AttachmentProjection {
    String getFileOriginalName();

    Long getFileSize();

    String getFileContentType();

    String getFileServerName();

    String getFileLocation();

    String getFileURL();
}
