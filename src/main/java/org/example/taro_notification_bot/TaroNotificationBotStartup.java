package org.example.taro_notification_bot;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TaroNotificationBotStartup {

    public static void main(String[] args) {
        ConfigReader configReader = new ConfigReader();
        TelegramBot telegramBot = new TelegramBot(configReader.get());

        try {
            telegramBot.initApi();
        } catch (TelegramApiException e) {
            System.exit(1);
        }
    }
}
