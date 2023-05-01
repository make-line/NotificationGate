package com.example.notificationgate.scheduler.telegram;

import com.example.notificationgate.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
public class TelegramService extends TelegramLongPollingBot {

    private final UserRepository userRepository;
    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = new SendMessage();
        if(userRepository.findByTelegramChatId(update.getMessage().getChatId().toString()).isPresent()) {
            String message = "Вы уже получаете сообщения от бота";
            sendMessage.setChatId(update.getMessage().getChatId().toString());
            sendMessage.setText(message);
        }
        else {
            String message = "Добро пожаловать! Чтобы зарегистрироваться, перейдите по ссылке: [Регистрация](http://localhost:8080/register-tg/" + update.getMessage().getChatId().toString() + ")";
            sendMessage.setChatId(update.getMessage().getChatId().toString());
            sendMessage.setText(message);
            sendMessage.setParseMode(ParseMode.MARKDOWN);
        }
        try {
            execute(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "CorpNotificationBot";
    }

    @Override
    public String getBotToken() {
        return "6249684159:AAFQcrLL3aOcln0uiPvVehMO_VdDzqh7X0Y";
    }

    public void sendMessageToUser(String message, String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


}