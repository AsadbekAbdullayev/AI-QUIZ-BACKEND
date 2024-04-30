package uz.genesis.aiquest.webserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.genesis.aiquest.webserver.commons.exception.GeneralApiException;
import uz.genesis.aiquest.webserver.models.dto.ConductedTestCreateDTO;
import uz.genesis.aiquest.webserver.models.dto.ConductedTestDTO;
import uz.genesis.aiquest.webserver.models.dto.DirectionDTO;
import uz.genesis.aiquest.webserver.models.dto.SubDirectionDTO;
import uz.genesis.aiquest.webserver.models.entity.ConductedTest;
import uz.genesis.aiquest.webserver.models.entity.DirectionTest;
import uz.genesis.aiquest.webserver.models.entity.SubDirection;
import uz.genesis.aiquest.webserver.models.entity.UserTalent;
import uz.genesis.aiquest.webserver.models.enums.TestType;
import uz.genesis.aiquest.webserver.models.mappers.ConductedTestMapper;
import uz.genesis.aiquest.webserver.repository.ConductedTestRepository;
import uz.genesis.aiquest.webserver.repository.SubDirectionRepository;
import uz.genesis.aiquest.webserver.utils.ErrorCodes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ConductedTestService {

    private final ConductedTestRepository conductedTestRepository;
    private final SubDirectionRepository subDirectionRepository;
    private final DirectionService directionService;
    private final ConductedTestMapper conductedTestMapper;

    public ConductedTest saveConductedTest(UserTalent userTalent, TestType testType, SubDirection subDirection) {
        ConductedTest conductedTest = new ConductedTest();
        conductedTest.setTestType(testType);
        conductedTest.setUserTalent(userTalent);
        conductedTest.setSubDirectionId(subDirection.getId());
        conductedTest.setStartDateTime(LocalDateTime.now());
        DirectionTest directionTest = subDirection.getDirectionTest();
        if (Objects.isNull(directionTest))
            throw new GeneralApiException("currently_we_cannot_provide_test_to_this_direction");
        conductedTest.setFinishDateTime(directionTest.getFinishDateTime(testType));
        return conductedTestRepository.save(conductedTest);
    }

    public ConductedTestCreateDTO startAiQuestionarioTest(UserTalent userTalent, Long subdirectionId) {
        //create conducted test
        boolean isUserAllowedToAttend = isUserAllowedToAttendWithDirection(userTalent.getId(), TestType.AI_QUESTIONARIO, subdirectionId);
        if (!isUserAllowedToAttend) {
            throw new GeneralApiException("user_already_attended_to_test", ErrorCodes.ERR_TEST_ALREADY_ATTENDED);
        }

        SubDirection subDirection = subDirectionRepository.findById(subdirectionId)
                .orElseThrow(GeneralApiException.throwEx("resource_not_found", ErrorCodes.ERR_NOT_FOUND));

        DirectionTest directionTest = subDirection.getDirectionTest();

        ConductedTest conductedTest = new ConductedTest(
                userTalent,
                LocalDateTime.now(),
                directionTest.getFinishDateTime(TestType.AI_QUESTIONARIO),
                null,
                TestType.AI_QUESTIONARIO,
                subDirection,
                subdirectionId,
                false,
                null,
                null,
                null

        );

        ConductedTest saved = conductedTestRepository.save(conductedTest);
        return new ConductedTestCreateDTO(saved.getId(), saved.getRemainedTimeInMilliseconds(), null);
    }


    public boolean isTestAlreadyInProgressByUserIdAndSubdirectionIdAndTestType(UUID userTalentId, TestType testType, Long subdirectionId) {
        return conductedTestRepository.existsByCurrentDateIsBetweenStartAndEndDateTimeAndByUserIdAndSubdirectionId(LocalDateTime.now(), userTalentId, testType, subdirectionId);
    }

    //agar user 1 hafta ichida bir marta testni urinib korgan bolsa demak u 1 hafta kutshi kerak, method tekshiradi 1
    public boolean isUserAllowedToAttendWithDirection(UUID userTalentId, TestType testType, Long subDirectionId) {
        //userni direction boyicha oxrgi yechgan testini ovolyapmz
        var conductedTestOptional = conductedTestRepository.findFirstByUserTalentIdAndTestTypeAndSubDirectionIdOrderByCreatedAtDesc(userTalentId, testType, subDirectionId);
        if (conductedTestOptional.isEmpty())
            //agar bo'sh bolsa hali bu direction boyicha testda qatnashmagan , true qaytaramiz ruhsat
            return true;

        var conductedTest = conductedTestOptional.get();
        //agar optional ichida qiymat bo'sa demak bir marta bosayam qatnashgan
        //testni yaratilgan vaqti hozrgi vaqtdan oldin bolsa demak true yani ruhsat test yechishga
        return conductedTest.getCreatedAt().toLocalDateTime()
                .isBefore(LocalDateTime.now()
                        .minusDays(7));
    }


    public ConductedTest getGeneratedTestByUserIdAndTestTypeAndSubdirectionId(UserTalent userTalent, TestType testType, Long subdirectionId) {
        //get last attended test by subdirectionId
        var lastAttendedTestOptional = conductedTestRepository.findFirstByUserTalentIdAndTestTypeAndSubDirectionIdOrderByCreatedAtDesc(userTalent.getId(), testType, subdirectionId);
        if (lastAttendedTestOptional.isEmpty())
            throw new GeneralApiException("user_not_attended_verification_tests_yet", ErrorCodes.ERR_BAD_REQUEST);

        return lastAttendedTestOptional.get();
    }

    public ConductedTest getById(UUID conductedTestId) {
        return conductedTestRepository.findById(conductedTestId)
                .orElseThrow(GeneralApiException.throwEx("resource_not_found", ErrorCodes.ERR_NOT_FOUND));
    }

    public List<ConductedTestDTO> getAllConductedTests(UUID talentId) {
        return conductedTestMapper.toDtoList(conductedTestRepository.findAllByUserTalentId(talentId));
    }

    public List<DirectionDTO> getDirectionsOfPassedStandardTestAssessment(UUID talentId) {
        Iterable<Long> subDirectionsIterableId = conductedTestRepository.findAllByUserTalentIdAndTestType(TestType.STANDARD_TEST.name(), talentId);

        List<SubDirection> passedSubDirectionsData = subDirectionRepository.findAllById(subDirectionsIterableId);
        List<DirectionDTO> directionDTOSToReturn = new ArrayList<>();
        for (DirectionDTO baseDirection : directionService.getList()) {
            DirectionDTO directionToBeAdded = new DirectionDTO();
            directionToBeAdded.setId(baseDirection.getId());
            directionToBeAdded.setCaption(baseDirection.getCaption());
            List<SubDirectionDTO> listOfSubDirections = getSubDirectionDTOS(baseDirection, passedSubDirectionsData);
            if (!listOfSubDirections.isEmpty()) {
                directionToBeAdded.setSubDirections(listOfSubDirections);
                directionDTOSToReturn.add(directionToBeAdded);
            }
        }
        return directionDTOSToReturn;
    }

    private static List<SubDirectionDTO> getSubDirectionDTOS(DirectionDTO baseDirection, List<SubDirection> passedSubDirectionsData) {
        List<SubDirectionDTO> listOfSubDirections = new ArrayList<>();
        for (SubDirection passedSubDirection : passedSubDirectionsData) {
            if (passedSubDirection.getDirection().getId().equals(baseDirection.getId())) {
                SubDirectionDTO subDirectionDTO = new SubDirectionDTO(passedSubDirection.getCaption(), passedSubDirection.getIsActive());
                subDirectionDTO.setId(passedSubDirection.getId());
                listOfSubDirections.add(subDirectionDTO);
            }

        }
        return listOfSubDirections;
    }

}
