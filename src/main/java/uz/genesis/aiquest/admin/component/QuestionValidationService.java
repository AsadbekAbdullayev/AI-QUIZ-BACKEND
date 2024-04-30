package uz.genesis.aiquest.admin.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.genesis.aiquest.webserver.models.entity.QuestionOption;
import uz.genesis.aiquest.webserver.models.enums.QuestionValidationState;
import uz.genesis.aiquest.webserver.repository.QuestionRepository;
import uz.genesis.aiquest.webserver.utils.PromptUtils;
import uz.genesis.aiquest.webserver.models.entity.Question;
import uz.genesis.aiquest.webserver.models.enums.EGPTType;
import uz.genesis.aiquest.webserver.models.enums.TestType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Component
@Log4j2
public class QuestionValidationService {

    private final OpenAiChatClient openAiChatClient;
    private final QuestionRepository questionRepository;
    private final ObjectMapper objectMapper;




    @Scheduled(fixedRate = 100000, initialDelay = 0)
    public void validateWithAI() throws JsonProcessingException {

        // Set up initial page request
        int pageNumber = 0; // Start with the first page
        int pageSize = 10; // Set your desired page size
        Page<Question> page;

        do {
            // Fetch a page of entities
            PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
            page = questionRepository.findAllByQuestionValidationStateAndForTestType(QuestionValidationState.PENDING, TestType.STANDARD_TEST, pageRequest);

            // Process entities in the current page
            for (Question question : page.getContent()) {
                List<QuestionOption> questionOptions = question.getQuestionOptions();

                List<String> optionCaptions = questionOptions.stream().map(QuestionOption::getOptionCaption).toList();

                try {
                    ChatResponse openaiResponse = openAiChatClient.call(userPrompt(question.getQuestionCaption(), question.getCodeExample(), optionCaptions, question.getDirection().getCaption()));
                    String content = openaiResponse.getResult().getOutput().getContent();
                    JsonNode jsonNode = objectMapper.readTree(content);
                    boolean isValid = jsonNode.get(PromptUtils.KeyConstants.is_valid).asBoolean();
                    boolean is_duplication_exist = jsonNode.get(PromptUtils.KeyConstants.is_duplication_exist).asBoolean();
                    boolean correct_answer_exists = jsonNode.get(PromptUtils.KeyConstants.correct_answer_exists).asBoolean();
                    String conclusion = jsonNode.get(PromptUtils.KeyConstants.conclusion).textValue();
                    String correct_answer_is = jsonNode.get(PromptUtils.KeyConstants.correct_answer_is).textValue();
                    if (isValid && correct_answer_exists && !is_duplication_exist) {
                        question.setQuestionValidationState(QuestionValidationState.VALID);
                        question.setAiFeedback(conclusion + "\n\n" + "The concluded right answer is " + correct_answer_is);
                        questionRepository.save(question);
                    }else {
                        question.setQuestionValidationState(QuestionValidationState.INVALID);
                        question.setAiFeedback(conclusion);
                        questionRepository.save(question);
                    }
                    if (openaiResponse.getMetadata().getRateLimit().getTokensLimit() < 500) {
                        Thread.sleep(openaiResponse.getMetadata().getRateLimit().getRequestsReset().toMillis());
                    }
                    if (openaiResponse.getMetadata().getRateLimit().getRequestsLimit() < 5) {
                        Thread.sleep(openaiResponse.getMetadata().getRateLimit().getRequestsReset().toMillis());
                    }
                    Thread.sleep(1000);

                } catch (Exception e) {
                    log.error("Exception occurred while checking question : ", e);
                    log.info("Question details : {}", question.toString());
                    log.info("Question answers : {}", optionCaptions);
                }
            }

            pageNumber++;
        } while (page.hasNext());
    }

    public String systemPrompt(String direction) {
        PromptTemplate promptTemplate = new PromptTemplate("""
                You are an assistant skilled in validating the accuracy of multiple-choice or one-choice questions and their answers. 
                The questions are related to {direction}. Ensure each question has one and only one correct answer, 
                Verify that answers do not contain duplicate content or multiple variations conveying the same meaning, ensuring uniqueness across options, and verify the question is related to given directions,
                conclusion should define why do you decide to
                """);
        promptTemplate.add("direction", direction);
        return promptTemplate.getTemplate();

    }

    public Prompt userPrompt(String questionCaption, String codeExample, List<String> answersCaption, String directionCaption) {
        StringBuilder answersString = new StringBuilder();
        int index = 0;
        for (String captionAnswer : answersCaption) {
            answersString.append(index++)
                    .append(". ")
                    .append(captionAnswer)
                    .append("\n");
            index++;
        }
        PromptTemplate promptTemplate = new PromptTemplate("""
                 Evaluate the following multiple-choice question and its answers: 
                 Q: {questionCaption}
                 {codeExample}
                 {answers}
                evaluate the accuracy of the question and ensure that it does not contain any additional misunderstood or misinterpreted words. you should identify any inaccuracies or misunderstandings present in the text and provide 
                suggestions or corrections where necessary, add your suggestion or correction to \'conclusion\' field.
                and also please identify the correct answer with caption of answer, ascertain if there are any duplicate or synonymous responses, 
                and confirm the accuracy of the question. The response should be in parseable JSON format with the following keys: \'is_valid\', \'is_duplication_exist\', \'correct_answer_exists\', \'correct_answer_is\', \'conclusion\'.
                 """);
        promptTemplate.add("answers", answersString);
        promptTemplate.add("questionCaption", questionCaption);
        promptTemplate.add("codeExample", Objects.requireNonNullElse(codeExample, ""));
        List<Message> instructions = promptTemplate.create().getInstructions();
        List<Message> messages = new ArrayList<>(instructions);
        messages.add(new SystemMessage(systemPrompt(directionCaption)));
        return new Prompt(messages, OpenAiChatOptions.builder().withModel(EGPTType.GPT_3_5.getCaption()).withResponseFormat(new
                OpenAiApi.ChatCompletionRequest.ResponseFormat("json_object")).withTemperature(0.3f).build());
    }
}
//gpt-4-1106-preview