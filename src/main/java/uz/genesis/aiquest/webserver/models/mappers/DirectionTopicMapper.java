package uz.genesis.aiquest.webserver.models.mappers;

import org.mapstruct.Mapper;
import uz.genesis.aiquest.webserver.models.dto.DirectionTopicDTO;
import uz.genesis.aiquest.webserver.models.entity.DirectionTopic;
import uz.genesis.aiquest.webserver.models.mappers.base.GenericMapper;

@Mapper(componentModel = "spring")
public interface DirectionTopicMapper extends GenericMapper<DirectionTopic, DirectionTopicDTO> {

}
