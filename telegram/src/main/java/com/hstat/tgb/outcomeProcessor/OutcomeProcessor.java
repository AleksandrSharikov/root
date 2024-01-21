package com.hstat.tgb.outcomeProcessor;

import com.hstat.dtoModels.TgMessage;
import com.hstat.tgb.telegram.Bot;
import org.springframework.stereotype.Service;

/**
 * Class for sending messages. For now is doing nothing. Made for the future
 */
@Service
public class OutcomeProcessor {
    private final Bot bot;

    public OutcomeProcessor(Bot bot) {
        this.bot = bot;
    }

    public void sendMessage(long chatId, String message){
        bot.sendMessage(chatId, message);
    }
    public void sendMessage(TgMessage message) { bot.sendMessage(message.chatId(), message.message());}
}
