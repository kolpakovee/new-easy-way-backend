package ru.kolpakovee.neweasyway.model.serpapi;

import java.util.Map;

public class GoogleSearch extends SerpApiSearch {
    public GoogleSearch(Map<String, String> parameter, String apiKey) {
        super(parameter, apiKey, "google_lens");
    }

    public GoogleSearch() {
        super("google_lens");
    }

    public GoogleSearch(Map<String, String> parameter) {
        super(parameter, "google_lens");
    }
}