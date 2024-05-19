package ru.kolpakovee.neweasyway.model.serpapi;


import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Price {
    private String value;
    @SerializedName("extracted_value")
    private double extractedValue;
    private String currency;
}
