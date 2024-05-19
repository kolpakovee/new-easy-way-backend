package ru.kolpakovee.neweasyway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.kolpakovee.neweasyway.constant.serpapi.SearchParams;

import java.util.HashMap;
import java.util.Map;

@Service
public class SerpApiHelper {

    @Value("${serpapi.key}")
    private String serpApiKey;

    public Map<String, String> getParams(String imageUrl) {
        Map<String, String> params = new HashMap<>();

        params.put(SearchParams.API_KEY, serpApiKey);
        params.put(SearchParams.ENGINE, SearchParams.GOOGLE_LENS);
        params.put(SearchParams.URL, imageUrl);
//        params.put(SearchParams.LANGUAGE, SearchParams.RU);
//        params.put(SearchParams.COUNTRY, SearchParams.RU);
        params.put("no_cache", "true");

        return params;
    }
}
