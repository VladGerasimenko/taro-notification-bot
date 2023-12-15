package org.example.taro_notification_bot;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    private final String username;
    private final Set<Long> joinedBefore;

    public TelegramBot(Properties properties) {
        super(properties.getProperty("bot.token"));
        this.username = properties.getProperty("bot.username");
        this.joinedBefore = new HashSet<>();
    }

    public void initApi() throws TelegramApiException {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            log.error("Failed to initialize TelegramApi");
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            log.info("Message update received {}", update.getMessage().toString());
            List<User> newChatMembers = update.getMessage().getNewChatMembers();
            if (newChatMembers != null) {
                for (User user : newChatMembers) {
                    if (!user.getIsBot()) {
                        if (!joinedBefore.contains(user.getId())) {
                            log.info("New user {} joined. Going to send welcome message", user);
                            sendWelcomeMessage(user);
                            joinedBefore.add(user.getId());
                        } else {
                            log.info("User {} joined before. Skip to send message", user);
                        }
                    }
                }
            }
        }
        else {
            log.info("Update {} received", update);
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    private void sendWelcomeMessage(User user) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(user.getId());
        sendMessage.setText("Hello!");

        log.info("Send hello to {}", user.getId());

        //Uncomment when correct text will be added
//        try {
//            this.execute(sendMessage);
//        } catch (TelegramApiException e) {
//            log.error("Failed to send message for {}", user);
//            log.error(e.getMessage(), e);
//        }
    }
}
