package uz.genesis.aiquest.webserver.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.genesis.aiquest.webserver.models.dto.Header;
import uz.genesis.aiquest.webserver.utils.RestConstants;
import uz.genesis.aiquest.webserver.models.entity.UserTalent;
import uz.genesis.aiquest.webserver.service.FileService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = RestConstants.TM_BASE_URL)
public class FileUploadingController {

    private final FileService fileService;

    @PostMapping(value = "/talent/file/upload")
    public Header<?> upload(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal UserTalent userTalent) throws IOException {
        return Header.ok(fileService.uploadFile(file, userTalent));
    }

    @GetMapping(value = "/file/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse httpServletResponse) throws IOException {
        fileService.downloadFile(httpServletResponse,fileName);
    }
}
