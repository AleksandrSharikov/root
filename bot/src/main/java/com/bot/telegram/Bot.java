package com.bot.telegram;

import com.bot.kafka.KafkaSender;
import com.hstat.common.dtoModels.TgMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * The telegram bot. Receives and sands messages.
 */

@Slf4j
@Component
public class Bot extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    private final KafkaSender kafkaSender;

    @Autowired
    public Bot(BotConfig botConfig, KafkaSender kafkaSender) {
        this.botConfig = botConfig;
        this.kafkaSender = kafkaSender;
    }

    /**
     * Receive message and send it to processor
     * @param update
     */
    @Override
    public void onUpdateReceived(Update update) {
        log.info("Received update: " + update.toString());
        if(update.getMessage().hasText()) {
            kafkaSender.receiveToQueue(new TgMessage(update.getMessage().getChatId(), update.getMessage().getText()));
            if(update.getMessage().getText().equals("/self_test")){
                sendMessage(update.getMessage().getChatId(), "Bot receive the message and send it immediately");
                kafkaSender.toOutcomeQueue(new TgMessage(update.getMessage().getChatId(), "Bot set this message to the Kafka queue and sand from it"));
            }
        }
    }

    /**
     * Sands message to certain chatId
     * @param chatId
     * @param textToSend
     */
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