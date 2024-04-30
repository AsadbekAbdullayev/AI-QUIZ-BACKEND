package uz.genesis.aiquest.webserver.models.dto.res;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class FileCreatedResponse {
    private String fileUrl;
    private String fileName;
    private Long fileSize;
    private UUID id;
}
