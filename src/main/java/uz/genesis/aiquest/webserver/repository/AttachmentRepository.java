package uz.genesis.aiquest.webserver.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.genesis.aiquest.admin.dto.projection.AttachmentProjection;
import uz.genesis.aiquest.webserver.models.entity.Attachment;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AttachmentRepository extends BaseRepository<Attachment, UUID>  {

    Optional<Attachment> findByFileServerName(String fileServerName);
@Query(value = "select a.file_location as fileLocation,a.file_content_type as fileContentType,a.file_size as fileSize, a.file_original_name as fileOriginalName, a.fileurl as fileURL, a.file_server_name as fileServerName from attachments a join courses c on a.id = c.attachment_id where c.id=?1",nativeQuery = true)
AttachmentProjection findById_For_Projection(Long id);
}
