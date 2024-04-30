package uz.genesis.aiquest.webserver.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.genesis.aiquest.webserver.models.dto.projection.TalentCountBySubDirectionProjection;
import uz.genesis.aiquest.webserver.models.entity.UserTalent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserTalentRepository extends BaseRepository<UserTalent, UUID> {

    Optional<UserTalent> findByEmailVerificationToken(String token);

    Optional<UserTalent> findByEmail(String email);

    @Query("select ut from UserTalent ut where ut.oneIdPin = ?1")
    Optional<UserTalent> findByOneIdPin(String pin);

    boolean existsByEmail(String email);

    @Query(value = "select sd.id as id , sd.caption,(select count(ut.id) from user_talents ut where ut.sub_direction_id=sd.id) from sub_directions sd", nativeQuery = true)
    List<TalentCountBySubDirectionProjection> getProjection();

    @Query(value = "select sd.id ,sd.caption,(select count(ut.id) from user_talents ut where ut.sub_direction_id=sd.id) from sub_directions sd where upper(caption) like %?1%", nativeQuery = true)
    List<TalentCountBySubDirectionProjection> searchSubDirection(String caption);

}
