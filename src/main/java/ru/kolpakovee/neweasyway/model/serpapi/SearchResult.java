package ru.kolpakovee.neweasyway.model.serpapi;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchResult {
    @SerializedName("search_metadata")
    private SearchMetadata searchMetadata;

    @SerializedName("search_parameters")
    private SearchParameters searchParameters;

    @SerializedName("visual_matches")
    private List<VisualMatch> visualMatches;
}
