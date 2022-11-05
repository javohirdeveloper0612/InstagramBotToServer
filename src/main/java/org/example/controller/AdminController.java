package org.example.controller;

import org.example.enums.StepStatus;
import org.example.telegramBot.MyTelegramBot;
import org.example.util.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Controller
public class AdminController {

    @Autowired
    private MainController mainController;
    @Autowired
    private MyTelegramBot myTelegramBot;

    private StepStatus step = StepStatus.END;

    public void start(Update update) {

        System.out.println(update.getMessage());

        System.out.println(update.getChannelPost());

        Message message = update.getMessage();
        if (message.hasText()) {
            String text = message.getText();
            if (text.equals("/start")) {
                menu(message);
            } else if (text.equals("/help")) {
                mainController.helpCommand(message);
            } else if (text.equals("/contact")) {
                mainController.contactCommand(message);
            } else if (text.equals("Reklama")) {

                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(message.getChatId());
                sendMessage.setText("Reklama yuboring: ");
                step = StepStatus.START;
                myTelegramBot.send(sendMessage);
                return;

            } else if (step.equals(StepStatus.START)) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId("5723825009");
                sendMessage.setText(text);
                step = StepStatus.END;
                myTelegramBot.send(sendMessage);
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
                myTelegramBot.send(sendMessage);
            }

        } else {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(message.getChatId());
            sendMessage.setText("This bot helps to get video and photo only from Instagram.\n" +
                    "\n" +
                    "Этот бот позволяет скачивать видео и фото только из Instagram.");
            myTelegramBot.send(sendMessage);
        }

    }

    public void menu(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Welcome Amin aka ");
        sendMessage.setReplyMarkup(Button.markup(Button.rowList(Button.row(
                Button.button("Reklama"), Button.button("User Count")
        ))));

        myTelegramBot.send(sendMessage);
    }


}


