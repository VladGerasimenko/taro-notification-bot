package org.example.taro_notification_bot;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Properties;

@Slf4j
public class TaroNotificationBotStartup {

    public static void main(String[] args) throws TelegramApiException{
        if (args.length != 1) {
            printUsage();
        } else {
            ConfigReader configReader = new ConfigReader();
            Properties properties = configReader.get();
            String botToken = args[0];
            properties.put("bot.token", botToken);
            new TelegramBot(properties).initApi();
        }
    }

    private static void printUsage() {
        log.info("-------------------Usage--------------------");
        log.info("-------------Command line args -------------");
        log.info("-------------(1) bot token -----------------");
    }
}
