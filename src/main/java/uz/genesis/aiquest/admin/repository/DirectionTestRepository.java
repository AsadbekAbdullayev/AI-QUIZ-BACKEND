package uz.genesis.aiquest.admin.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.genesis.aiquest.webserver.models.entity.DirectionTest;
import uz.genesis.aiquest.webserver.repository.BaseRepository;

@Repository
public interface DirectionTestRepository extends BaseRepository<DirectionTest, Long> {

    boolean existsBySubDirectionId(Long directionId);
    @Query(value = "select count(*) from questions q join talentdb.public.direction_tests dt ON q.sub_direction_id=dt.sub_direction_id where q.for_test_type=:testType and q.question_validation_state='VALID' and q.sub_direction_id=:id",nativeQuery = true)
    int testQuantity(String testType,Long id);
}
