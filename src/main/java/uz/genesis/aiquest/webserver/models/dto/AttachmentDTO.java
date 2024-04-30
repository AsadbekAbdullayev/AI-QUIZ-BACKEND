package uz.genesis.aiquest.webserver.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttachmentDTO {
    private String fileOriginalName;

    private Long fileSize;

    private String fileContentType;

    private String fileServerName;

    private String fileLocation;

    private String fileURL;

}
