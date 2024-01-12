package com.hstat.tgb.outcomeProcessor;

import com.hstat.tgb.telegram.Bot;
import org.springframework.stereotype.Service;

@Service
public class OutcomeProcessor {
    private final Bot bot;

    public OutcomeProcessor(Bot bot) {
        this.bot = bot;
    }

    public void sendMessage(long chatId, String message){
        bot.sendMessage(chatId, message);
    }
}
