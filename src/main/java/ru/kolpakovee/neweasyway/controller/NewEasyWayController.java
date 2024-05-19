package ru.kolpakovee.neweasyway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import ru.kolpakovee.neweasyway.model.response.ProductCodeResponse;
import ru.kolpakovee.neweasyway.model.serpapi.SerpApiSearchException;
import ru.kolpakovee.neweasyway.service.NewEasyWayService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class NewEasyWayController {

    private final NewEasyWayService newEasyWayService;

    @PostMapping("/features")
    public ProductCodeResponse getProductFeaturesByPhoto(@RequestParam("file") MultipartFile file) throws IOException, SerpApiSearchException {
        return newEasyWayService.getProductFeaturesByPhoto(file);
    }

    @PostMapping("/code")
    public ProductCodeResponse getCode(@RequestParam MultipartFile file) throws IOException {
        return newEasyWayService.getProductCodeByPhoto(file);
    }
}
