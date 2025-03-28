package ru.example.ComplimentBot;

/**
 * @author onegines
 * @date 26.03.2025
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ru.example.ComplimentBot.services.ComplimentService;
import ru.example.ComplimentBot.services.TelegramBotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScheduledComplimentSender {

    private final TelegramBotService telegramBotService;
    private final ComplimentService complimentService;
    @Value("${telegram.bot.chat-id}")
    private Long chatId;  // Получение chatId из application.properties

    @Autowired
    public ScheduledComplimentSender(TelegramBotService telegramBotService, ComplimentService complimentService) {
        this.telegramBotService = telegramBotService;
        this.complimentService = complimentService;
    }

    @Scheduled(cron = "0 */15 * * * ?")
    public void sendDailyCompliment() {
        String compliment = complimentService.getRandomCompliment();
        telegramBotService.sendMessage(chatId, compliment);
        log.info("✅ Отправлен комплимент: {}", compliment);
    }

}