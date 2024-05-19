package ru.kolpakovee.neweasyway.model.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.List;

@Data
public class ProductCodeResponse {
    private List<String> codes;

    public static ProductCodeResponse jsonToProductCodeResponse (String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);

        // Извлекаем поле content
        JsonNode contentNode = rootNode.path("choices").get(0).path("message").path("content");

        // Извлекаем JSON-строку из content
        String content = contentNode.asText();
        String jsonInContent = extractJsonFromContent(content);

        return objectMapper.readValue(jsonInContent, ProductCodeResponse.class);
    }

    // Метод для извлечения JSON-строки из содержимого ответа
    private static String extractJsonFromContent(String content) {
        int startIndex = content.indexOf("{");
        int endIndex = content.lastIndexOf("}") + 1;
        if (startIndex != -1) {
            return content.substring(startIndex, endIndex);
        }
        return "";
    }
}
