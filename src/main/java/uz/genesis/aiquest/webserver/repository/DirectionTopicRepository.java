package uz.genesis.aiquest.webserver.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.genesis.aiquest.webserver.models.entity.DirectionTopic;

import java.util.List;

@Repository
public interface DirectionTopicRepository extends BaseRepository<DirectionTopic,Long> {

    Boolean existsBySubDirectionId(Long subdirectionId);

    List<DirectionTopic> findAllBySubDirectionId(Long subdirectionId);

    @Query(value = """
            SELECT *
            FROM direction_topics
            WHERE sub_direction_id = :sub_direction_id
            ORDER BY RANDOM()
            LIMIT 1;
                        
            """, nativeQuery = true)
    DirectionTopic getRandomDirectionTopic(@Param("sub_direction_id") Long subdirectionId);
}
