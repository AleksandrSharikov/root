package com.hstat.tgb.sendToTg;

import com.hstat.common.dtoModels.TgMessage;
import com.hstat.tgb.kafka.KafkaSender;
import org.springframework.stereotype.Service;

/**
 * Class for sending messages. For now is doing nothing. Made for the future
 */
@Service
public class TgSenderImpl implements TgSender {

    private final KafkaSender kafkaSender;

    public TgSenderImpl(KafkaSender kafkaSender) {
        this.kafkaSender = kafkaSender;
    }


    public void sendMessage(long chatId, String message){
       kafkaSender.sendKafkaTg(new TgMessage(chatId, message));
    }
    public void sendMessage(TgMessage message) {kafkaSender.sendKafkaTg(message);}
}
