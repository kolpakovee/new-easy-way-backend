package ru.kolpakovee.neweasyway.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlCleaner {
    public static String clean(String html) {
        Document document = Jsoup.parse(html);

        Elements elements = document.select("*"); // Выбираем все элементы

        StringBuilder cleanText = new StringBuilder();

        for (Element element : elements) {
            String text = element.ownText();
            String filteredText = text.replaceAll("[^\\p{L}\\p{N}\\s]", "");            cleanText.append(filteredText).append(" ");
        }

        String finalText = cleanText.toString().trim();
        finalText = finalText.replaceAll("\\s{3,}", " ");

        return finalText;
    }
}
