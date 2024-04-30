package uz.genesis.aiquest.admin.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.genesis.aiquest.webserver.models.dto.res.FileCreatedResponse;
import uz.genesis.aiquest.webserver.models.entity.AdminAccount;
import uz.genesis.aiquest.webserver.models.entity.Attachment;
import uz.genesis.aiquest.webserver.repository.AttachmentRepository;
import uz.genesis.aiquest.webserver.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminFileService {

    private final AttachmentRepository attachmentRepository;
    private final Path path = Path.of("../nfs/document");

    @Value(value = "${file.server.domain-name}")
    public String serverURL;


    @PostConstruct
    public void createDir() throws IOException {
        Files.createDirectories(path);
    }


    public FileCreatedResponse uploadFile(MultipartFile multipartFile, AdminAccount admin) throws IOException {
        var fileServerName = String.format("%s_admin_%s", UUID.randomUUID(),admin.getFirstName());
        var fileLocation = Path.of(path.toFile().getPath() + File.separator + fileServerName + "." + multipartFile.getOriginalFilename());
        String downloadUrl = Utils.generateDownloadUrl(fileServerName, serverURL);
        Attachment attachment = new Attachment();
        attachment.setFileLocation(fileLocation.toFile().getAbsolutePath());
        attachment.setFileSize(multipartFile.getSize());
        attachment.setFileContentType(multipartFile.getContentType());
        attachment.setFileOriginalName(multipartFile.getOriginalFilename());
        attachment.setFileServerName(fileServerName);
        attachment.setFileURL(downloadUrl);
        Files.copy(multipartFile.getInputStream(),fileLocation ,StandardCopyOption.REPLACE_EXISTING);
        Attachment savedAttachment = attachmentRepository.save(attachment);
        return FileCreatedResponse.builder().fileName(multipartFile.getOriginalFilename())
                .fileUrl(downloadUrl)
                .fileSize(multipartFile.getSize())
                .id(savedAttachment.getId())
                .build();

    }
}
