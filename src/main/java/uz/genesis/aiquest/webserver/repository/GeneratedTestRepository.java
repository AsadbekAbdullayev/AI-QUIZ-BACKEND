package uz.genesis.aiquest.webserver.repository;

import org.springframework.stereotype.Repository;
import uz.genesis.aiquest.webserver.models.entity.GeneratedTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GeneratedTestRepository extends BaseRepository<GeneratedTest, UUID> {

    Optional<GeneratedTest> findByConductedTestIdAndQuestionIndex(UUID conductedTestId, Integer questionIndex);


    List<GeneratedTest> findByConductedTestId(UUID conductedTestId);
}
