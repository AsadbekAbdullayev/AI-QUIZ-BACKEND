package uz.genesis.aiquest.webserver.models.mappers;

import org.mapstruct.Mapper;
import uz.genesis.aiquest.webserver.models.dto.QuestionDTO;
import uz.genesis.aiquest.webserver.models.entity.Question;
import uz.genesis.aiquest.webserver.models.mappers.base.GenericMapper;

@Mapper(componentModel = "spring", uses = {QuestionOptionMapper.class})
public interface QuestionMapper extends GenericMapper<Question, QuestionDTO> {
}
