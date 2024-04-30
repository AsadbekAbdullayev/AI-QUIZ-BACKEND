package uz.genesis.aiquest.webserver.models.mappers;

import org.mapstruct.Mapper;
import uz.genesis.aiquest.webserver.models.dto.QuestionOptionDTO;
import uz.genesis.aiquest.webserver.models.entity.QuestionOption;
import uz.genesis.aiquest.webserver.models.mappers.base.GenericMapper;

@Mapper(componentModel = "spring")
public interface QuestionOptionMapper extends GenericMapper<QuestionOption, QuestionOptionDTO> {
}
