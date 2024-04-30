package uz.genesis.aiquest.webserver.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.genesis.aiquest.webserver.models.entity.SubDirection;

@Repository
public interface SubDirectionRepository extends BaseRepository<SubDirection, Long> {
    @Query(value = "select sd.caption from sub_directions sd where sd.id=:id",nativeQuery = true)
    String getSubdirectionCaption(Long id);
}
