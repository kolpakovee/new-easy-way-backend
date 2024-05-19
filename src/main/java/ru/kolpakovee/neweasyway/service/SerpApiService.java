package ru.kolpakovee.neweasyway.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kolpakovee.neweasyway.config.SerpApiHelper;
import ru.kolpakovee.neweasyway.model.serpapi.GoogleSearch;
import ru.kolpakovee.neweasyway.model.serpapi.SearchResult;
import ru.kolpakovee.neweasyway.model.serpapi.SerpApiSearchException;
import ru.kolpakovee.neweasyway.model.serpapi.VisualMatch;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SerpApiService {

    private final SerpApiHelper helper;

    public List<String> getLinksByPhoto(String imageUrl) throws SerpApiSearchException {
        GoogleSearch search = new GoogleSearch(helper.getParams(imageUrl));

        JsonObject serpApiResults;

        serpApiResults = search.getJson();

        Gson gson = new Gson();
        SearchResult result = gson.fromJson(serpApiResults, SearchResult.class);

        return result.getVisualMatches()
                .stream()
                .map(VisualMatch::getLink)
                .toList();
    }
}
