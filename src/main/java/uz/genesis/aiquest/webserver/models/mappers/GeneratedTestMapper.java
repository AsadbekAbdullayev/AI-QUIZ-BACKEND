package uz.genesis.aiquest.webserver.models.mappers;

import org.mapstruct.Mapper;
import uz.genesis.aiquest.webserver.models.dto.GeneratedTestDTO;
import uz.genesis.aiquest.webserver.models.entity.GeneratedTest;
import uz.genesis.aiquest.webserver.models.mappers.base.GenericMapper;

@Mapper(componentModel = "spring", uses = QuestionAnswerMapper.class)
public interface GeneratedTestMapper extends GenericMapper<GeneratedTest, GeneratedTestDTO> {


}
