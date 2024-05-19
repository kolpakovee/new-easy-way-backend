package ru.kolpakovee.neweasyway.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.kolpakovee.neweasyway.model.response.ProductCodeResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Service
public class ChatGptService {

    private final WebClient webClient;
    private final String model;
    private final String apiKey;

    public ChatGptService(@Value("${chatgpt.api.key}") String apiKey,
                          @Value("${chatgpt.api.model}") String model) {
        this.model = model;
        this.apiKey = apiKey;
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    //TODO: хранить контекст сообщений, чтобы все они были связаны
    //TODO: добавить фото
    public String sendMessage(String message) {
        String requestBody = String.format("{\"model\": \"%s\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]}", model, message);

        String response = webClient.post()
                .uri("/chat/completions")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return extractMessageFromJSONResponse(response);
    }

    public ProductCodeResponse sendMessageWithImage(String message, String photoUrl) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode requestBody = mapper.createObjectNode();
        requestBody.put("model", "gpt-4o");

        ObjectNode userMessage = mapper.createObjectNode();
        userMessage.put("role", "user");

        ObjectNode textContent = mapper.createObjectNode();
        textContent.put("type", "text");
        textContent.put("text", message);

        ObjectNode imageUrlContent = mapper.createObjectNode();
        imageUrlContent.put("type", "image_url");
        imageUrlContent.putObject("image_url").put("url", photoUrl);

        userMessage.set("content", mapper.createArrayNode().add(textContent).add(imageUrlContent));
        requestBody.set("messages", mapper.createArrayNode().add(userMessage));
        requestBody.put("max_tokens", 300);

        String jsonRequestBody = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestBody);

        var response = webClient.method(HttpMethod.POST)
                .uri("/chat/completions")
                .bodyValue(jsonRequestBody)
                .retrieve()
                .bodyToMono(String.class);

        System.out.println(response);

        return ProductCodeResponse.jsonToProductCodeResponse(response.block());
    }

    private String extractMessageFromJSONResponse(String response) {
        int start = response.indexOf("content") + 11;
        int end = response.indexOf("\"", start);
        return response.substring(start, end);
    }
}

