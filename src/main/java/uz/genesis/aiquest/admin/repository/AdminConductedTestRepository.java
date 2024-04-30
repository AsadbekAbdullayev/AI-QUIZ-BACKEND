package uz.genesis.aiquest.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.genesis.aiquest.admin.dto.projection.ConductedTestProjection;
import uz.genesis.aiquest.webserver.models.entity.ConductedTest;
import uz.genesis.aiquest.webserver.repository.BaseRepository;

import java.util.UUID;

@Repository
public interface AdminConductedTestRepository extends BaseRepository<ConductedTest, UUID> {


    @Query(value = """
            select ct.created_at       as createdAt,
                   ct.user_talent_id   as userTalentId,
                   ct.final_score      as finalScore,
                   ct.finish_date_time as finishedTime,
                   ct.start_date_time  as startedTime,
                   ct.test_type        as testType,
                   ct.submitted_time   as submittedTime,
                   ct.sub_direction_id as subdirectionId,
                   ut.email            as email

            from conducted_tests ct
                     inner join user_talents ut on ct.user_talent_id = ut.id where  ct.final_score is not null
                                    """, nativeQuery = true, countQuery = """
            
            """)
    Page<ConductedTestProjection> getConductedTestsList(Pageable pageable);
}