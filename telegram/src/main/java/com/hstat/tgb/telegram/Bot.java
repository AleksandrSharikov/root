package com.hstat.tgb.telegram;

import com.hstat.tgb.messagesProcessing.IncomeProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class Bot extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    private final IncomeProcessor incomeProcessor;

    @Autowired
    public Bot(BotConfig botConfig, IncomeProcessor incomeProcessor) {
        this.botConfig = botConfig;
        this.incomeProcessor = incomeProcessor;
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("Received update: " + update.toString());
        incomeProcessor.process(update);
    }

    public void sendMessage(Long chatId, String textToSend) {
        if (textToSend.length() > 3900) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(chatId));
            sendMessage.setText("Internal bot error. Too long message");
        } else {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(chatId));
            sendMessage.setText(textToSend);
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                log.error("TelegramApiException thrown in sendMessage");
                log.error(e.getMessage());
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }


}