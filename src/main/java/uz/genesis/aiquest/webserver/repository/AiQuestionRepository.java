package uz.genesis.aiquest.webserver.repository;

import org.springframework.stereotype.Repository;
import uz.genesis.aiquest.webserver.models.entity.AiQuestion;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AiQuestionRepository extends BaseRepository<AiQuestion, UUID> {
    int countByConductedTestId(UUID conductedTestId);

    Optional<AiQuestion> findByQuestionIndexAndConductedTestId(Integer questionIndex, UUID conductedTestId);

    List<AiQuestion> findAllByConductedTestIdAndUserTalentId(UUID conductedTestId, UUID userTalentId);
}
