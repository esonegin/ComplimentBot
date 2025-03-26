package ru.example.ComplimentBot.services;

/**
 * @author onegines
 * @date 26.03.2025
 */
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class ComplimentService {
    private final List<String> compliments = List.of(
            "Ты сегодня выглядишь потрясающе! 😍",
            "У тебя невероятно красивая улыбка 😊",
            "Ты – настоящий вдохновитель! 💡",
            "Ты умеешь делать этот мир лучше! 🌍",
            "Ты особенный человек! 💖"
    );

    private final Random random = new Random();

    public String getRandomCompliment() {
        return compliments.get(random.nextInt(compliments.size()));
    }
}