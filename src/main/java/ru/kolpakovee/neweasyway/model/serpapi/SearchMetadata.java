package ru.kolpakovee.neweasyway.model.serpapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchMetadata {
    private String id;
    private String status;
    private String jsonEndpoint;
    private String createdAt;
    private String processedAt;
    private String googleLensUrl;
    private String rawHtmlFile;
    private String prettifyHtmlFile;
    private double totalTimeTaken;
}
