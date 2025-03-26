package ru.example.ComplimentBot;

/**
 * @author onegines
 * @date 26.03.2025
 */
import ru.example.ComplimentBot.services.ComplimentService;
import ru.example.ComplimentBot.services.TelegramBotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledComplimentSender {

    private final TelegramBotService telegramBotService;
    private final ComplimentService complimentService;

    @Scheduled(cron = "0 0 9 * * ?")  // Каждое утро в 9:00
    public void sendDailyCompliment() {
        String compliment = complimentService.getRandomCompliment();
        telegramBotService.sendMessage("123456789", compliment);  // Укажи свой chatId
        log.info("Отправлен комплимент: {}", compliment);
    }
}