package com.hstat.tgb.botMessageProcessing;

import com.hstat.common.dtoModels.TgMessage;
import com.hstat.tgb.telegram.Bot;
import org.springframework.stereotype.Service;

@Service
public class OutcomeProcessor {
    private final Bot bot;

    public OutcomeProcessor(Bot bot) {
        this.bot = bot;
    }

    public void sendMessage(TgMessage message){
        bot.sendMessage(message.chatId(), message.message());
    }
}
