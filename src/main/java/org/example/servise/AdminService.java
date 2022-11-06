package org.example.servise;

import org.example.modul.Profile;
import org.example.repository.UserRepository;
import org.example.telegramBot.MyTelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MyTelegramBot myTelegramBot;

    public void usersList(Message message){
       List<Profile> profileList = userRepository.getUsersList();

        for (Profile profile : profileList) {
            if (profile != null){
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(message.getChatId());
                sendMessage.setText("Id:" + profile.getId() +"\n" +
                                "Name: " +profile.getName() +"\n" +
                                "UserName: @" + profile.getUserName() +"\n" +
                                "UserId: " + profile.getUserId()+"\n\n"+
                        "\uD83D\uDCE5 @isnta_video_bot");
                myTelegramBot.send(sendMessage);
            }
        }

    }

    public void userCount(Message message) {

        long count = userRepository.getUsersCount();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Botdan hozirda: " + count + " foydalanuvchi mavjud \n\n" +
                "\uD83D\uDCE5 @isnta_video_bot");
        myTelegramBot.send(sendMessage);


    }
}
