package ru.kolpakovee.neweasyway.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.kolpakovee.neweasyway.model.response.ProductCodeResponse;
import ru.kolpakovee.neweasyway.service.ChatGptService;

@RestController
@RequiredArgsConstructor
public class ChatGptController {

    private final ChatGptService chatGptService;

    @GetMapping("/chat")
    public String getChatResponse(@RequestParam String message) {
        return chatGptService.sendMessage(message);
    }

    @GetMapping("/chat_with_image")
    public ProductCodeResponse getResponseByImage(@RequestParam String message, @RequestParam String photoUrl) throws JsonProcessingException {
        return chatGptService.sendMessageWithImage(message, photoUrl);
    }
}

