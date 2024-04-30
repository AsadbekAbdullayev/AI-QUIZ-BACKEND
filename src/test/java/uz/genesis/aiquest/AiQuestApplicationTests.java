package uz.genesis.aiquest;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.AntPathMatcher;
import uz.genesis.aiquest.webserver.models.dto.DirectionTopicDTO;
import uz.genesis.aiquest.webserver.utils.Utils;
import uz.genesis.aiquest.webserver.service.DirectionTopicService;

import java.util.List;

@RequiredArgsConstructor
@SpringBootTest
class AiQuestApplicationTests {

    @Autowired
    private OpenAiChatClient openAiChatClient;


    @Autowired
    private DirectionTopicService directionTopicService;

    @Test
    public void aa() throws JsonProcessingException {
        List<DirectionTopicDTO> directionTopicDTOS = directionTopicService.generateOrGetFromDbDirectionTopics(1L);
        System.out.println(directionTopicDTOS);
    }


    @Test
    void contextLoads() {
        Double proportion = Utils.proportion(2.5);
        Double proportion2 = Utils.proportion(0.5);
        Assertions.assertEquals(150, proportion);
        Assertions.assertEquals(-100, proportion2);
    }

    @Test
    void testAntPathMatcher() {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        boolean match = antPathMatcher.match("http://localhost:8080/swagger-ui/**", "http://localhost:8080/swagger-ui/css/index.html");
        Assertions.assertTrue(match);
    }

    @Test
    void testExtractUsernameLinkedinURL() {
        String url = "https://www.linkedin.com/in/husanboy-sobirjonov-24506122b/";
        String username = "husanboy-sobirjonov-24506122b";
        Assertions.assertEquals(username, Utils.extractUsername(url));
    }

    @Test
    void testPinfl() {
        for (int i = 0; i < 1000; i++) {

            String s = Utils.generatePINFL();
            System.out.println(s);
            Assertions.assertEquals(14, s.length());
        }
    }
}
