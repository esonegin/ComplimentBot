package ru.example.ComplimentBot.services;

/**
 * @author onegines
 * @date 26.03.2025
 */
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class ComplimentService {
    private static final String COMPLIMENT_API_URL = "https://tools-api.robolatoriya.com/compliment?type=1";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ComplimentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getRandomCompliment() {
        String response = restTemplate.getForObject(COMPLIMENT_API_URL, String.class);

        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            return jsonNode.get("text").asText();
        } catch (Exception e) {
            return "Ошибка получения комплимента 😢";
        }
    }
}