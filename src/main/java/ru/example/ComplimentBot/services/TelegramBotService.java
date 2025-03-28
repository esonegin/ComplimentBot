package ru.example.ComplimentBot.services;

/**
 * @author onegines
 * @date 26.03.2025
 */
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Service
public class TelegramBotService extends TelegramLongPollingBot {

    private final String botUsername;
    private final String chatId;
    private final ComplimentService complimentService;

    public TelegramBotService(@Value("${telegram.bot.token}") String botToken,
                              @Value("${telegram.bot.username}") String botUsername,
                              @Value("${telegram.bot.chat-id}") String chatId,
                              ComplimentService complimentService) {
        super(botToken);
        this.botUsername = botUsername;
        this.chatId = chatId;
        this.complimentService = complimentService;

        if (chatId == null || chatId.isEmpty()) {
            log.error("❌ Chat ID не задан! Укажите telegram.bot.chat-id в application.properties");
        }
        log.info("✅ Telegram бот запущен: username = {}, chatId = {}", botUsername, chatId);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatIdOnline = update.getMessage().getChatId();
            String message = update.getMessage().getText();
            log.info("📩 Получено сообщение от {}: {}", chatIdOnline, message);

            switch (message.toLowerCase()) {
                case "/start":
                    sendMessage(chatIdOnline, "Привет! Я буду присылать тебе комплименты 😊");
                    break;
                case "/compliment":
                    String compliment = complimentService.getRandomCompliment();
                    sendMessage(chatIdOnline, compliment);
                    break;
                default:
                    sendMessage(chatIdOnline, "Я понимаю только команды: /start, /compliment");
            }
        }
    }

    public void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message);
            log.info("✅ Сообщение отправлено: {}", text);
        } catch (TelegramApiException e) {
            log.error("❌ Ошибка отправки сообщения", e);
        }
    }
}