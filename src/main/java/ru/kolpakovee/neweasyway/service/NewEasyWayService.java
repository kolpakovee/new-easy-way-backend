package ru.kolpakovee.neweasyway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import ru.kolpakovee.neweasyway.model.response.ProductCodeResponse;
import ru.kolpakovee.neweasyway.model.serpapi.SerpApiSearchException;
import ru.kolpakovee.neweasyway.utils.HtmlCleaner;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NewEasyWayService {

    @Value("${neweasyway.limit}")
    private int limit;

    private final S3Service s3Service;
    private final SerpApiService serpApiService;
    private final ChatGptService chatGptService;

    private final WebClient webClient;

    public ProductCodeResponse getProductFeaturesByPhoto(MultipartFile photo) throws IOException, SerpApiSearchException {
        UUID uuid = UUID.randomUUID();

        String photoUrl = s3Service.uploadFile(uuid.toString(), photo);

        System.out.printf("Фото %s сохранено в S3\n", uuid);

        List<String> links = serpApiService.getLinksByPhoto(photoUrl);

        System.out.printf("Получено %s ссылок из SerpApi\n", links.size());

        List<String> features = links.stream()
                .map(link -> {
                    String html = webClient.get()
                            .uri(link)
                            .retrieve()
                            .onStatus(httpStatus -> httpStatus.value() != 200,
                                    clientResponse -> Mono.empty())
                            .bodyToMono(String.class)
                            .onErrorResume(WebClientResponseException.class, e -> {
                                System.err.println("Error fetching URL: " + link + " Status: " + e.getStatusCode());
                                return Mono.empty(); // Пропускаем запрос при ошибке
                            })
                            .block();
                    System.out.println(link + "\n");
                    return html;
                })
                .filter(Objects::nonNull) // Удаляем null значения, которые могли появиться из-за Mono.empty()
                .map(HtmlCleaner::clean)
                .peek(System.out::println)
                .map(cleanHtml ->
                {
                    String message = cleanHtml + "Определи характеристики товара и верни набор кодов ТН ВЭД которые ему подходят";
                    System.out.printf("Отправляю сообщение в chatGpt: %s\n", message);
                    String response = chatGptService.sendMessage(message);
                    System.out.printf("Получен ответ от chatGpt: %s\n", response);
                    return response;
                })
                .limit(limit)
                .toList();

        StringBuilder message = new StringBuilder("Опередели возможные коды ТН ВЭД по изображению и характеристикам из текста. Верни JSON формата \n{\n" +
                "  \"codes\": [\n" +
                "    \"123456\",\n" +
                "    \"789012\",\n" +
                "    \"345678\"\n" +
                "  ]\n" +
                "}\nХарактеристики:");

        for (var feature : features) {
            message.append("\n").append(feature);
        }

        return chatGptService.sendMessageWithImage(message.toString(), photoUrl);
    }

    public ProductCodeResponse getProductCodeByPhoto(MultipartFile file) throws IOException {
        UUID uuid = UUID.randomUUID();

        String photoUrl = s3Service.uploadFile(uuid.toString(), file);
        String message = "Опередели возможные коды ТН ВЭД по изображению и характеристикам из текста. Верни JSON формата {\n" +
                "  \"codes\": [\n" +
                "    \"123456\",\n" +
                "    \"789012\",\n" +
                "    \"345678\"\n" +
                "  ]\n" +
                "}\n";

        return chatGptService.sendMessageWithImage(message, photoUrl);
    }
}
