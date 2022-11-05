package org.example;

import org.example.config.Config;
import org.example.repository.UserRepository;
import org.example.telegramBot.MyTelegramBot;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        MyTelegramBot myTelegramBot = (MyTelegramBot) applicationContext.getBean("myTelegramBot");

try {
    TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
    telegramBotsApi.registerBot(myTelegramBot);
} catch (TelegramApiException e) {
    throw new RuntimeException(e);
}



    }
}