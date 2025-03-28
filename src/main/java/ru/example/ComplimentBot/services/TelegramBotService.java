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

@Service
@Slf4j
public class TelegramBotService extends TelegramLongPollingBot {

    private final String botUsername;
    private final String chatId;


    public TelegramBotService(@Value("${telegram.bot.token}") String botToken,
                              @Value("${telegram.bot.username}") String botUsername,
                              @Value("${telegram.bot.chat-id}") String chatId) {
        super(botToken);
        this.botUsername = botUsername;
        this.chatId = chatId;

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
        // Получаем chatId из обновления
        long chatIdOnline = update.getMessage().getChatId();

        // Сохраняем chatId (например, в базу данных или в памяти)
        log.info("Получен chatId: {}", chatIdOnline);
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            log.info("📩 Получено сообщение: {}", message);

            if (message.equalsIgnoreCase("/start")) {
                sendMessage(chatIdOnline, "Привет! Я буду присылать тебе комплименты 😊");
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