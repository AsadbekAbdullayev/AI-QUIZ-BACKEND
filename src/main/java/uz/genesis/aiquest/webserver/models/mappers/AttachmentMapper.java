package uz.genesis.aiquest.webserver.models.mappers;

import org.mapstruct.Mapper;
import uz.genesis.aiquest.webserver.models.dto.AttachmentDTO;
import uz.genesis.aiquest.webserver.models.mappers.base.GenericMapper;
import uz.genesis.aiquest.webserver.models.entity.Attachment;

@Mapper(componentModel = "spring")
public interface AttachmentMapper extends GenericMapper<Attachment, AttachmentDTO> {

}
