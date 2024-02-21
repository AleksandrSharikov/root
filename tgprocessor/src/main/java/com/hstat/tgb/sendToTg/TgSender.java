package com.hstat.tgb.sendToTg;

import com.hstat.common.dtoModels.TgMessage;

public interface TgSender {
    void sendMessage(long chatId, String message);
    void sendMessage(TgMessage message);
}
