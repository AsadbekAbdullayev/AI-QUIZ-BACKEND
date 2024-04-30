package uz.genesis.aiquest.webserver.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.context.i18n.LocaleContextHolder;
import uz.genesis.aiquest.webserver.models.entity.DirectionTopic;
import uz.genesis.aiquest.webserver.models.entity.AiQuestion;
import uz.genesis.aiquest.webserver.models.entity.SubDirection;
import uz.genesis.aiquest.webserver.models.enums.EGPTType;
import uz.genesis.aiquest.webserver.service.ai.AiQuestionAnswerPromptDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PromptUtils {

    public interface KeyConstants {
        String is_valid = "is_valid";
        String is_duplication_exist = "is_duplication_exist";
        String correct_answer_exists = "correct_answer_exists";
        String conclusion = "conclusion";
        String correct_answer_is = "correct_answer_is";
        String recommendations = "recommendations";
        String seniority_level = "seniority_level";
        String approximate_percentage = "approximate_percentage";

    }
    public static Prompt getTopicGeneratePrompt(SubDirection subDirection) {
        PromptTemplate promptTemplate = new PromptTemplate("""
                I'm planning to create a series of lessons focused on {DIRECTION}.
                Please generate a comprehensive list of topics that should be covered in these lessons. 
                The list should contain at least 50 topics.  And that topics should be more specific not generic
                response should be json string array with key 'topics'
                                """);

        promptTemplate.add("DIRECTION", subDirection.getCaption());
        List<Message> instructions = promptTemplate.create().getInstructions();
        List<Message> messages = new ArrayList<>(instructions);
        messages.add(new SystemMessage(systemPromptForTopicGeneration(subDirection.getCaption())));
        return new Prompt(messages, OpenAiChatOptions.builder().withModel(EGPTType.GPT_4_WITH_JSON_MODE.getCaption()).withResponseFormat(new
                        OpenAiApi.ChatCompletionRequest.ResponseFormat("json_object"))
                .withTemperature(0.3f).build());
    }

    public static Prompt getCreateAiQuestionPrompt(DirectionTopic directionTopic) {
        PromptTemplate promptTemplate = new PromptTemplate("""
                Your task is to create 1 question to given topic
                Here is the topic: {topic}
                                
                response should be json format with key 'question'
                language: {language}
                """);

        promptTemplate.add("topic", directionTopic.getTopicName());
        promptTemplate.add("language", LocaleContextHolder.getLocale().getLanguage());

        List<Message> instructions = promptTemplate.create().getInstructions();
        List<Message> messages = new ArrayList<>(instructions);
        messages.add(new SystemMessage(systemPromptForAiQuestionPrompt(directionTopic
                .getSubDirection().getCaption())));
        return new Prompt(messages, OpenAiChatOptions
                .builder()
                .withModel(EGPTType.GPT_4_WITH_JSON_MODE.getCaption())
                .withResponseFormat(new OpenAiApi.ChatCompletionRequest
                        .ResponseFormat("json_object"))
                .withTemperature(0.4f).build());
    }

    private static String systemPromptForAiQuestionPrompt(String direction) {
        PromptTemplate promptTemplate = new PromptTemplate("""
                You are an assistant skilled in Information technologies and programming languages,
                you can generate questions based on given topic and {direction} and based on candidates answers we can analyze skills and
                how experienced our candidate by giving real scenarios and problems and to check how candidate can solve that problems
                """);
        promptTemplate.add("direction", direction);
        return promptTemplate.getTemplate();
    }

    public static String systemPromptForTopicGeneration(String direction) {
        PromptTemplate promptTemplate = new PromptTemplate("""
                You are an assistant skilled in Information technologies and programming languages,
                 you can give list of topics to create learning materials topics related to given direction
                """);
        promptTemplate.add("direction", direction);
        return promptTemplate.getTemplate();
    }

    public static Prompt submitQuestionsAndAnswersPrompt(List<AiQuestion> aiQuestions, String subDirection, ObjectMapper objectMapper) throws JsonProcessingException {
        List<AiQuestionAnswerPromptDTO> questionAnswerPromptDTOList = aiQuestions.stream()
                .map(
                        aiQuestion -> new AiQuestionAnswerPromptDTO(aiQuestion.getAiQuestion(),
                                Objects.requireNonNullElse(aiQuestion.getUserAnswer(),
                                        "not_provided_by_candidate"))
                ).toList();

        String questionAnswersJson = objectMapper.writeValueAsString(questionAnswerPromptDTOList);
        PromptTemplate promptTemplate = new PromptTemplate("""
                I will provide several questions along with responses from our candidates. Please evaluate their answers by calculating the approximate percentage of the correct answer of question addressed in each response.\s
                Evaluate answers and find mistakes or weak sides of candidate and add some recommendations.
                Additionally, assess their level of seniority based on their answers. The seniority levels to be assigned are: NO_LEVEL, INTERN, YOUNG_JUNIOR, STRONG_JUNIOR, MIDDLE, STRONG_MIDDLE, SENIOR.
                context is : {DIRECTION}
                Here is questions and answers:
                                
                {QUESTION_ANSWERS}     
                                
                response should be json format.
                json keys are : 'seniority_level', 'approximate_percentage', 'recommendations', 'conclusion'\s
                """);
        promptTemplate.add("DIRECTION", subDirection);
        promptTemplate.add("QUESTION_ANSWERS", questionAnswersJson);
        List<Message> instructions = promptTemplate.create().getInstructions();
        List<Message> messages = new ArrayList<>(instructions);
        messages.add(new SystemMessage(systemPromptForSubmissionOfQAiQuestionnarie()));
        return new Prompt(messages, OpenAiChatOptions
                .builder()
                .withModel(EGPTType.GPT_4_WITH_JSON_MODE.getCaption())
                .withResponseFormat(new OpenAiApi.ChatCompletionRequest
                        .ResponseFormat("json_object"))
                .withTemperature(0.1f).build());

    }

    private static String systemPromptForSubmissionOfQAiQuestionnarie() {
        return """
                You are an assistant skilled in Information technologies and programming languages, you can analyze and verify the candidate 
                by his answers that given in interview
                """;

    }
}
