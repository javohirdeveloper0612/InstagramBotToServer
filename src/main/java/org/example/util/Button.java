package org.example.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Button {
    public static org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton button(String text) {
        org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton keyboardButton = new org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton();
        keyboardButton.setText(text);
        return keyboardButton;
    }

    public static KeyboardRow row(org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton... keyboardButtons){
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.addAll(Arrays.asList(keyboardButtons));
        return keyboardRow;
    }

    public static List<KeyboardRow> rowList(KeyboardRow... keyboardRows){
        List<KeyboardRow> rows = new LinkedList<>();
        rows.addAll(Arrays.asList(keyboardRows));

        return rows;
    }

    public static ReplyKeyboardMarkup markup (List<KeyboardRow> rowList){
        ReplyKeyboardMarkup replyKeyboardMarkup =new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(rowList);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }
}
