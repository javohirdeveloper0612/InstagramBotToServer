package org.example.telegramBot;

import org.example.controller.AdminController;
import org.example.controller.MainController;
import org.example.servise.UserServise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
public class MyTelegramBot extends TelegramLongPollingBot {
    @Autowired
    private MainController mainController;
    @Autowired
    private UserServise userServise;
    @Autowired
    private AdminController adminController;


    @Override
    public void onUpdateReceived(Update update) {

        if (update.getMessage().getFrom().getId() == 1024661500){
            adminController.start(update);
            return;
        }

        if (update.hasMessage()) {
            Message message = update.getMessage();
            System.out.println(message.getFrom().getId());
            System.out.println(message.getFrom().getFirstName());

            System.out.println(message.getPhoto());

            if (message.hasText()) {
                String text = message.getText();
                if (text.equals("/start")) {
                    mainController.handle(message);
                } else if (text.equals("/help")) {
                    mainController.helpCommand(message);
                } else if (text.equals("/contact")) {
                    mainController.contactCommand(message);
                } else if (text.startsWith("https://www.instagram.com/p")) {
                    mainController.getUrlAndSendVideo(message, text);
                } else if (text.startsWith("https://www.instagram.com/reel")) {
                    mainController.getUrlAndSendVideoReel(message, text);
                } else if (text.startsWith("https://www.instagram.com/tv")) {
                    mainController.getUrlAndSendVideoReel(message, text);
                } else {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(message.getChatId());
                    sendMessage.setText("Sorry, Something wrong, or an invalid link. Please try again or check your url");
                    send(sendMessage);
                }

            } else {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(message.getChatId());
                sendMessage.setText("This bot helps to get video and photo only from Instagram.\n" +
                        "\n" +
                        "Этот бот позволяет скачивать видео и фото только из Instagram.");
                send(sendMessage);
            }
        } else {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId());
            sendMessage.setText("This bot helps to get video and photo only from Instagram.\n" +
                    "\n" +
                    "Этот бот позволяет скачивать видео и фото только из Instagram.");
            send(sendMessage);
        }
    }

    public void send(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(SendVideo sendVideo) {
        try {
            execute(sendVideo);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(SendPhoto sendPhoto) {
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String getBotUsername() {
        return "isnta_video_bot";
    }

    @Override
    public String getBotToken() {
        return "5663085130:AAGjxbYkrEq5KEw6loXvByJrF4aMVJmlbOI";
    }
}
