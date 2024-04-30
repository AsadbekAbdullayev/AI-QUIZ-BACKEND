package uz.genesis.aiquest.admin.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.genesis.aiquest.admin.dto.projection.TestPassedCountProjection;
import uz.genesis.aiquest.admin.dto.projection.NewRegisteredTalentCountProjection;
import uz.genesis.aiquest.webserver.models.entity.UserTalent;
import uz.genesis.aiquest.webserver.models.enums.TestType;
import uz.genesis.aiquest.webserver.repository.BaseRepository;

import java.util.UUID;

@Repository
public interface AdminUserTalentRepository extends BaseRepository<UserTalent, UUID> {

    @Query(value = """
             select (select count(cs)
                   from conducted_tests cs
                   where cs.created_at >= DATE_TRUNC('day', NOW())
                     and cs.test_type = :#{#testType?.name()}
                     and cs.is_verified = :isPassed) as testPassedCount,
                  (coalesce((select count(cs)
                             from conducted_tests cs
                             where cs.created_at >= DATE_TRUNC('day', NOW())
                               and cs.test_type = :#{#testType?.name()}
                               and cs.is_verified = :isPassed) / nullif((select count(cs)
                                                                         from conducted_tests cs
                                                                         where cs.created_at >= DATE_TRUNC('day', NOW() - interval '1 day')
                                                                         and cs.created_at <= DATE_TRUNC('day', NOW())
                                                                           and cs.test_type = :#{#testType?.name()}
                                                                           and cs.is_verified = :isPassed),
                                                                        0),
                            0))                      as testPassedCountProportion
            """, nativeQuery = true)
    TestPassedCountProjection getCountByTestTypePassedDaily(@Param(value = "isPassed") Boolean isPassed, @Param(value = "testType") TestType testType);


    @Query(value = """

            select (select count(cs)
                   from conducted_tests cs
                   where cs.created_at >= DATE_TRUNC('week', NOW())
                     and cs.test_type = :#{#testType?.name()}
                     and cs.is_verified = :isPassed) as testPassedCount,
                  (coalesce((select count(cs)
                             from conducted_tests cs
                             where cs.created_at >= DATE_TRUNC('week', NOW())
                               and cs.test_type = :#{#testType?.name()}
                               and cs.is_verified = :isPassed) / nullif((select count(cs)
                                                                         from conducted_tests cs
                                                                         where cs.created_at >= DATE_TRUNC('week', NOW() - interval '1 week')
                                                                         and cs.created_at <= DATE_TRUNC('week', NOW())
                                                                           and cs.test_type = :#{#testType?.name()}
                                                                           and cs.is_verified = :isPassed),
                                                                        0),
                            0))                      as testPassedCountProportion""", nativeQuery = true)
    TestPassedCountProjection getCountByTestTypePassedWeekly(@Param(value = "isPassed") Boolean isPassed, @Param(value = "testType") TestType testType);


    @Query(value = """
            select (select count(cs)
               from conducted_tests cs
               where cs.created_at >= DATE_TRUNC('month', NOW())
                 and cs.test_type = :#{#testType?.name()}
                 and cs.is_verified = :isPassed) as testPassedCount,
              (coalesce((select count(cs)
                         from conducted_tests cs
                         where cs.created_at >= DATE_TRUNC('month', NOW())
                           and cs.test_type = :#{#testType?.name()}
                           and cs.is_verified = :isPassed) / nullif((select count(cs)
                                                                     from conducted_tests cs
                                                                     where cs.created_at >= DATE_TRUNC('month', NOW() - interval '1 month')
                                                                     and cs.created_at <= DATE_TRUNC('month', NOW())
                                                                       and cs.test_type = :#{#testType?.name()}
                                                                       and cs.is_verified = :isPassed),
                                                                    0),
                        0))                      as testPassedCountProportion""", nativeQuery = true)
    TestPassedCountProjection getCountByTestTypePassedMonthly(@Param(value = "isPassed") Boolean isPassed, @Param(value = "testType") TestType testType);

    @Query(value = """
            select (select count(ut)
                    from user_talents ut
                    where ut.created_at >= DATE_TRUNC('day', NOW())
                      and ut.is_enabled)                         as newRegistered,
                   coalesce((select count(ut)
                             from user_talents ut
                             where ut.created_at >= DATE_TRUNC('day', NOW())
                               and ut.is_enabled)
                                /
                            nullif((select count(ut)
                                    from user_talents ut
                                    where ut.created_at >= DATE_TRUNC('day', NOW() - interval '1 day')
                                    and ut.created_at <= DATE_TRUNC('day', NOW())
                                      and ut.is_enabled), 0), 0) as newRegisteredProportion
            """, nativeQuery = true)
    NewRegisteredTalentCountProjection getNewRegisteredCountDaily();

    @Query(value = """
            select (select count(ut)
                    from user_talents ut
                    where ut.created_at >= DATE_TRUNC('week', NOW())
                      and ut.is_enabled)                         as newRegistered,
                   coalesce((select count(ut)
                             from user_talents ut
                             where ut.created_at >= DATE_TRUNC('week', NOW())
                               and ut.is_enabled)
                                /
                            nullif((select count(ut)
                                    from user_talents ut
                                    where ut.created_at >= DATE_TRUNC('week', NOW() - interval '1 week')
                                    and ut.created_at <= DATE_TRUNC('week', NOW())
                                      and ut.is_enabled), 0), 0) as newRegisteredProportion
            """, nativeQuery = true)
    NewRegisteredTalentCountProjection getNewRegisteredCountWeekly();

    @Query(value = """
            select (select count(ut)
                    from user_talents ut
                    where ut.created_at >= DATE_TRUNC('month', NOW())
                      and ut.is_enabled)                         as newRegistered,
                   coalesce((select count(ut)
                             from user_talents ut
                             where ut.created_at >= DATE_TRUNC('month', NOW())
                               and ut.is_enabled)
                                /
                            nullif((select count(ut)
                                    from user_talents ut
                                    where ut.created_at >= DATE_TRUNC('month', NOW() - interval '1 month')
                                    and ut.created_at <= DATE_TRUNC('month', NOW())
                                      and ut.is_enabled), 0), 0) as newRegisteredProportion
            """, nativeQuery = true)
    NewRegisteredTalentCountProjection getNewRegisteredCountMonthly();



}
