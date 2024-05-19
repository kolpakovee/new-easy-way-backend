package ru.kolpakovee.neweasyway.constant.chatgpt;

public class ChatGptMessage {
    public static String PROMPT = "Извлеки из html-содержимого страницы характеристики товара, верни ответ вида:\n" +
            "public class Product Features Response {\n" +
            "    private String productName;\n" +
            "    private Map<String, String> features;\n" +
            "}\n" +
            "html содержимое:";
}
