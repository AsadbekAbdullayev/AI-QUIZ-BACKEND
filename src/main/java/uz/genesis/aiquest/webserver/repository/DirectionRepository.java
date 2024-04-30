package uz.genesis.aiquest.webserver.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.genesis.aiquest.admin.dto.projection.CourseDirectionProjection;
import uz.genesis.aiquest.webserver.models.entity.Direction;

@Repository
public interface DirectionRepository extends BaseRepository<Direction, Long> {

    @Query(value = "SELECT d.caption as direction,sd.caption as subDirection, sd.id as subDirectionId from directions as d  join sub_directions sd on d.id = sd.direction_id join courses c on sd.id = c.subdirection_id where c.id = ?1",nativeQuery = true)
    CourseDirectionProjection findById_For_Projection(Long id);

}
