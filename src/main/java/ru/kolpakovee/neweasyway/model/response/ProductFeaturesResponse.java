package ru.kolpakovee.neweasyway.model.response;

import lombok.Data;

import java.util.Map;

@Data
public class ProductFeaturesResponse {
    private String productName;
    private Map<String, String> features;
}
