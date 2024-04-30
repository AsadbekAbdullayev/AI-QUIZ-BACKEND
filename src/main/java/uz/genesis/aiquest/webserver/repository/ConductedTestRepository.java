package uz.genesis.aiquest.webserver.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import uz.genesis.aiquest.webserver.models.entity.ConductedTest;
import uz.genesis.aiquest.webserver.models.enums.TestType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConductedTestRepository extends BaseRepository<ConductedTest, UUID> {


    @Query(value = """
            select case when count(ct) = 0 then false else true end
            from conducted_tests ct
            where ct.user_talent_id = :userTalentId
              and ct.finish_date_time >= :currentDateTime
              and ct.start_date_time <= :currentDateTime
              and ct.test_type =:#{#testType.name()}
              and ct.sub_direction_id =:subdirectionId
              and ct.submitted_time is null
            """, nativeQuery = true)
    Boolean existsByCurrentDateIsBetweenStartAndEndDateTimeAndByUserIdAndSubdirectionId(@Param(value = "currentDateTime") LocalDateTime currentDateTime, @RequestParam(value = "userTalentId") UUID userTalentId, @Param(value = "testType") TestType testType, @Param(value = "subdirectionId") Long subdirectionId);

    //oldin profile test otkizilgan va verified bogan bosa va 14 kun o'tmagan bosa -> true
    @Query(value = "select distinct c.sub_direction_id from conducted_tests as c where test_type = ?1 and c.user_talent_id= ?2 and c.is_verified=true",nativeQuery = true)
    List<Long> findAllByUserTalentIdAndTestType(String testType, UUID userTalent_id);


    List<ConductedTest> findAllByUserTalentId(UUID userTalentId);

    Optional<ConductedTest> findFirstByUserTalentIdAndTestTypeAndSubDirectionIdOrderByCreatedAtDesc(UUID userTalentId, TestType testType, Long subdirectionId);

}
