package ru.kolpakovee.neweasyway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kolpakovee.neweasyway.model.serpapi.SerpApiSearchException;
import ru.kolpakovee.neweasyway.service.SerpApiService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SerpApiController {

    public final SerpApiService serpApiService;

    @GetMapping("/links")
    public List<String> getLinksByPhoto(@RequestParam String imageUrl) throws SerpApiSearchException {
        return serpApiService.getLinksByPhoto(imageUrl);
    }
}
