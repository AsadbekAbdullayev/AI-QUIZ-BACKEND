package uz.genesis.aiquest.admin.repository;

import org.springframework.stereotype.Repository;
import uz.genesis.aiquest.webserver.models.entity.AdminAccount;
import uz.genesis.aiquest.webserver.repository.BaseRepository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminAccountRepository extends BaseRepository<AdminAccount, UUID> {
    boolean existsByEmail(String superAdminEmail);

    Optional<AdminAccount> findByEmail(String email);
}
