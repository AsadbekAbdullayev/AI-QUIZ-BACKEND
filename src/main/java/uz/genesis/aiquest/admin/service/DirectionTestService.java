package uz.genesis.aiquest.admin.service;

import org.springframework.stereotype.Service;
import uz.genesis.aiquest.admin.repository.DirectionTestRepository;
import uz.genesis.aiquest.webserver.models.dto.CustomPage;
import uz.genesis.aiquest.webserver.models.dto.DirectionTestDTO;
import uz.genesis.aiquest.webserver.utils.ErrorCodes;
import uz.genesis.aiquest.webserver.commons.baseservice.Deletable;
import uz.genesis.aiquest.webserver.commons.baseservice.GenericService;
import uz.genesis.aiquest.webserver.commons.exception.GeneralApiException;
import uz.genesis.aiquest.webserver.models.dto.req.PaginationRequest;
import uz.genesis.aiquest.webserver.models.entity.DirectionTest;
import uz.genesis.aiquest.webserver.models.enums.TestType;
import uz.genesis.aiquest.webserver.models.mappers.DirectionTestMapper;
import uz.genesis.aiquest.webserver.repository.QuestionRepository;

@Service
public class DirectionTestService extends GenericService<DirectionTest, Long, DirectionTestDTO, DirectionTestRepository, DirectionTestMapper> implements Deletable {

    private final QuestionRepository questionRepository;
    private final DirectionTestRepository directionTestRepository;

    public DirectionTestService(DirectionTestRepository repository, DirectionTestMapper mapper, QuestionRepository questionRepository, DirectionTestRepository directionTestRepository) {
        super(repository, mapper, DirectionTest.class);
        this.questionRepository = questionRepository;
        this.directionTestRepository = directionTestRepository;
    }


    @Override
    public DirectionTestDTO create(DirectionTestDTO dto) {
        if (repository.existsBySubDirectionId(dto.getSubDirectionId())) {
            throw new GeneralApiException("direction_test_already_added", ErrorCodes.ERR_BAD_REQUEST);
        }
        return super.create(dto);
    }

    public int getValidTestQuantityByType(TestType testType, Long subDirectionId) {
        return directionTestRepository.testQuantity(testType.name(), subDirectionId);
    }


    @Override
    public CustomPage<DirectionTestDTO> getListPagination(PaginationRequest paginationRequest) {
        var page = super.getListPagination(paginationRequest);
        page.getData().forEach(directionTestDTO -> directionTestDTO.setTotalQuestionQuantity(questionRepository
                .countByDirectionId(directionTestDTO.getSubDirectionId())));
        return page;
    }
}
