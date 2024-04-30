package uz.genesis.aiquest.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.genesis.aiquest.webserver.models.dto.AttachmentDTO;
import uz.genesis.aiquest.webserver.models.dto.base.BaseDTO;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SocialMediaDto extends BaseDTO<UUID> {

    private String link;

    private String logoFileUrl;

    private AttachmentDTO attachment;

    private UUID attachmentId;

    private String name;
}
