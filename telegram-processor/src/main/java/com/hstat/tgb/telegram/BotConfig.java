package com.hstat.tgb.telegram;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Bot config
 */
@Configuration
public class BotConfig {

    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String token;

    private final String version = "1.0 Very beginning";

    public String getBotName() {
        return botName;
    }

    public String getToken() {
        return token;
    }

    public String getVersion(){return version;}
}
