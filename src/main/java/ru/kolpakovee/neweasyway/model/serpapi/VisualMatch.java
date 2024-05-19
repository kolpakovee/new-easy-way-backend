package ru.kolpakovee.neweasyway.model.serpapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VisualMatch {
    private int position;
    private String title;
    private String link;
    private String source;
    private String sourceIcon;
    private Price price;
    private String thumbnail;
    private boolean inStock;
}
