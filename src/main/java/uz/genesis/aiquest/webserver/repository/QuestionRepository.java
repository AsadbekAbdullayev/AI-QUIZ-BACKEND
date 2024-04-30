package uz.genesis.aiquest.webserver.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.genesis.aiquest.webserver.models.enums.QuestionValidationState;
import uz.genesis.aiquest.webserver.models.entity.Question;
import uz.genesis.aiquest.webserver.models.enums.QuestionLanguage;
import uz.genesis.aiquest.webserver.models.enums.TestType;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends BaseRepository<Question, UUID> {

    @Query(value = """

             SELECT
                *
            FROM
             questions q where q.question_validation_state='VALID' and q.sub_direction_id=:directionId and q.question_language=:lang and q.for_test_type =:#{#testType.name()}
                 order by
                          random()
                LIMIT :count
             """, nativeQuery = true)
    List<Question> generateRandomQuestions(@Param(value = "directionId") Long directionId, @Param(value = "count") Integer count, @Param("lang") String questionLanguage, @Param(value = "testType") TestType testType);


    Integer countByDirectionId(Long directionId);

    Page<Question> findAllByQuestionLanguageAndDirectionId(QuestionLanguage questionLanguage, Pageable pageable, Long subDirectionId);
    Page<Question> findAllByQuestionLanguageAndDirectionIdAndForTestType(QuestionLanguage questionLanguage, Pageable pageable, Long subDirectionId,TestType testType);


    Page<Question> findAllByQuestionValidationStateAndForTestType(QuestionValidationState questionValidationState, TestType testType, Pageable pageable);

}
