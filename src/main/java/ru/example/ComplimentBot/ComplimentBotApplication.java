package ru.example.ComplimentBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.example.ComplimentBot.services.TelegramBotService;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class ComplimentBotApplication {
	public static void main(String[] args) {
		SpringApplication.run(ComplimentBotApplication.class, args);
		try {
			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
			botsApi.registerBot(new TelegramBotService());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
