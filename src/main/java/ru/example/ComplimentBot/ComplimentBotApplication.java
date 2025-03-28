package ru.example.ComplimentBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.example.ComplimentBot.services.TelegramBotService;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
@EnableScheduling
public class ComplimentBotApplication {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ComplimentBotApplication.class, args);

		try {
			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
			TelegramBotService botService = context.getBean(TelegramBotService.class);
			botsApi.registerBot(botService);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
