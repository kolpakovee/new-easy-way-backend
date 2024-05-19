package ru.kolpakovee.neweasyway.model.serpapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchParameters {
    private String engine;
    private String url;
}
