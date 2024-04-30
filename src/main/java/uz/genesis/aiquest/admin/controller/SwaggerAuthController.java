package uz.genesis.aiquest.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.charset.Charset;

@Controller
@RequestMapping(value = "/swagger-auth")
@RequiredArgsConstructor
public class SwaggerAuthController {

    @Value(value = "classpath:templates/SwaggerAuth.html")
    private Resource resource;

    @GetMapping
    @ResponseBody
    public String getAuthPage() throws IOException {
        return resource.getContentAsString(Charset.defaultCharset());
    }
}
