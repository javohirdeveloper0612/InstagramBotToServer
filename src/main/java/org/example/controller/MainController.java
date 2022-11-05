package org.example.controller;


import org.example.modul.ApiModules;
import org.example.telegramBot.MyTelegramBot;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Controller
public class MainController {
    @Autowired
    private MyTelegramBot myTelegramBot;


    public void handle(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("🔥 Hi! This bot helps you to save photos, videos, carousels and many more from Instagram.\n" +
                "To get photo/video/carousel/reels/IGTV send URL of the post to the bot 🔗🔗.\n\n" +
                "🔥 Привет! Бот позволяет сохранять фото, все типы видео, галереи и прочее из Instagram.\n" +
                "Чтобы скачать фото/видео/галерею/рилз/IGTV просто пришлите ссылку на пост.\n" +
                "Чтобы скачать фото и описание профиля отправьте юзернейм 🔗🔗");
        myTelegramBot.send(sendMessage);


    }

    public void getUrlAndSendVideo(Message message, String url) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.bhawanigarg.com/social/instagram/?url=" + url)).build();
        HttpResponse<String> response = null;

        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.out.println("Not Found ! ");
        }


        JSONObject jsonObject = new JSONObject(response.body());
        JSONObject graphql = jsonObject.getJSONObject("graphql");
        JSONObject shortcode = graphql.getJSONObject("shortcode_media");

        String type = shortcode.getString("__typename");

        if (type.equals("GraphImage")) {
            ApiModules object = new ApiModules();

//            JSONArray resources = shortcode.getJSONArray("display_resources");
            object.setResources(shortcode.getJSONArray("display_resources"));
            JSONObject imageObject = object.getResources().getJSONObject(0);

//            String urlImage = imageObject.getString("src");
            object.setImageUrl(imageObject.getString("src"));

            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(message.getChatId());
            InputFile inputFile = new InputFile();
            inputFile.setMedia(object.getImageUrl());

            sendPhoto.setPhoto(inputFile);
            sendPhoto.setCaption(url + "\n\n" +
                    "\uD83D\uDCE5 @isnta_video_bot");
            myTelegramBot.send(sendPhoto);
            return;

        } else if (type.equals("GraphSidecar")) {

            JSONObject edge = shortcode.getJSONObject("edge_sidecar_to_children");
            JSONArray edges = edge.getJSONArray("edges");
            for (int i = 0; i < edges.length(); i++) {

                JSONObject url1 = edges.getJSONObject(i);
                JSONObject node = url1.getJSONObject("node");
                String typename = node.getString("__typename");
                if (typename.equals("GraphVideo")) {
                    String videoUrl = node.getString("video_url");

                    SendVideo sendVideo = new SendVideo();
                    sendVideo.setChatId(message.getChatId());
                    InputFile inputFile = new InputFile();
                    inputFile.setMedia(videoUrl);
                    sendVideo.setVideo(inputFile);
                    sendVideo.setCaption(url + "\n\n" +
                            "\uD83D\uDCE5 @isnta_video_bot");
                    myTelegramBot.send(sendVideo);
                    System.out.println(videoUrl);

                } else if (typename.equals("GraphImage")) {
                    String imageUrl = node.getString("display_url");
                    SendPhoto sendPhoto = new SendPhoto();
                    sendPhoto.setChatId(message.getChatId());
                    InputFile inputFile = new InputFile();
                    inputFile.setMedia(imageUrl);
                    sendPhoto.setPhoto(inputFile);
                    sendPhoto.setCaption(url + "\n\n" +
                            "\uD83D\uDCE5 @isnta_video_bot");

                    myTelegramBot.send(sendPhoto);

                }

            }
        }

        boolean is_video = shortcode.getBoolean("is_video");


        if (is_video) {
            String videoUrl = shortcode.getString("video_url");

            SendVideo sendVideo = new SendVideo();
            sendVideo.setChatId(message.getChatId());
            InputFile inputFile = new InputFile();
            inputFile.setMedia(videoUrl);
            sendVideo.setVideo(inputFile);
            sendVideo.setCaption(url + "\n\n" +
                    "\uD83D\uDCE5 @isnta_video_bot");
            System.out.println(videoUrl);

            myTelegramBot.send(sendVideo);
        }


    }

    public void getUrlAndSendVideoReel(Message message, String url) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.bhawanigarg.com/social/instagram/?url=" + url)).build();
        HttpResponse<String> response = null;

        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());


            JSONObject jsonObject = new JSONObject(response.body());
            JSONObject object = jsonObject.getJSONObject("graphql");
            JSONObject shortcode = object.getJSONObject("shortcode_media");

            String urlVideo1 = shortcode.getString("video_url");
            if (urlVideo1.equals(null)) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(message.getChatId());
                sendMessage.setText("Not Found!");
                myTelegramBot.send(sendMessage);
            }

            System.out.println(urlVideo1);

            SendVideo sendVideo = new SendVideo();
            sendVideo.setChatId(message.getChatId());
            InputFile inputFile = new InputFile();
            inputFile.setMedia(urlVideo1);
            sendVideo.setVideo(inputFile);
            sendVideo.setCaption(url + "\n\n" +
                    "\uD83D\uDCE5 @isnta_video_bot");


            myTelegramBot.send(sendVideo);

        } catch (RuntimeException | IOException | InterruptedException e) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(message.getChatId());
            sendMessage.setText("Sorry, Something wrong, or an invalid link. Please try again or check your url");
            myTelegramBot.send(sendMessage);
        }

    }

    public void helpCommand(Message message) {
        SendVideo sendVideo = new SendVideo();
        InputFile inputFile = new InputFile();
        inputFile.setMedia("BAACAgIAAxkBAAIB9mNlBme_hQSfDrFy5OEnO6Irwyv5AAKWGgAC9GgpS9PNJCius22RKgQ");
        sendVideo.setChatId(message.getChatId());
        sendVideo.setVideo(inputFile);
        sendVideo.setCaption("This video shows how to use the bot \n\n" +
                "@insta_video_bot");
        myTelegramBot.send(sendVideo);
    }

    public void contactCommand(Message message) {
        SendPhoto sendPhoto = new SendPhoto();
        InputFile inputFile = new InputFile();
        inputFile.setMedia("AgACAgIAAxkBAAIB-2NlCZmGO5e02JZ5PzDHAgWL3AmjAAKxwDEb9GgpSzSHy8GyWJVeAQADAgADcwADKgQ");
        sendPhoto.setChatId(message.getChatId());
        sendPhoto.setPhoto(inputFile);
        sendPhoto.setCaption("Send a message to this bot and we will respond @javaDeveloper_bot \n\n" +
                "gitHub account https://github.com/javohirdeveloper0612?tab=repositories\n\n" +
                "@insta_video_bot");

        myTelegramBot.send(sendPhoto);
    }
}
