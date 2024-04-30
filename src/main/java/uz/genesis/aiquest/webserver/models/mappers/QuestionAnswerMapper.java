package uz.genesis.aiquest.webserver.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import uz.genesis.aiquest.webserver.models.dto.QuestionAnswerDTO;
import uz.genesis.aiquest.webserver.models.entity.QuestionAnswer;
import uz.genesis.aiquest.webserver.models.mappers.base.GenericMapper;

@Mapper(componentModel = "spring")
public interface QuestionAnswerMapper extends GenericMapper<QuestionAnswer, QuestionAnswerDTO> {
    @Mappings(
            @Mapping(
                    source = "questionOption.optionCaption",
                    target = "optionCaption"
            )
    )
    @Override
    QuestionAnswerDTO toDto(QuestionAnswer entity);
}
