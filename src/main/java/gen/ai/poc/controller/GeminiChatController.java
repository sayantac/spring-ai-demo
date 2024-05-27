package gen.ai.poc.controller;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.Media;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatClient;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@RestController
public class GeminiChatController {

    private final VertexAiGeminiChatClient chatClient;

    @Autowired
    public GeminiChatController(VertexAiGeminiChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/ai/generate")
    public Map<String,String> generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of("generation", chatClient.call(message));
    }

    @GetMapping("/ai/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return chatClient.stream(prompt);
    }

    @GetMapping("/ai/weather")
    public String getWeather(@RequestParam(value = "message", defaultValue = "What's the weather like in San Francisco, Tokyo, and Paris?") String message) {
        UserMessage userMessage = new UserMessage(message);

        ChatResponse response = chatClient.call(new Prompt(List.of(userMessage),
                VertexAiGeminiChatOptions.builder().withFunction("CurrentWeather").build())); // (1) Enable the function

        return response.getResult().getOutput().getContent();
    }

    @GetMapping("/ai/multimodal")
    public String getPictureInfo(@RequestParam(value = "message", defaultValue = "Explain what do you see on this picture?") String message) {
        try {
            byte[] data = new ClassPathResource("/img.png").getContentAsByteArray();

            var userMessage = new UserMessage(message,
                    List.of(new Media(MimeTypeUtils.IMAGE_PNG, data)));

            ChatResponse response = chatClient.call(new Prompt(List.of(userMessage)));

            return response.getResult().getOutput().getContent();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
