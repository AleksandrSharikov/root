package com.hstat.tgb.mailKafka;

import com.hstat.common.dtoModels.StatSend;
import com.hstat.tgb.kafka.KafkaSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DialogSender {

    @Value("${topic.name}")
    private String tgStatTopic;

    private final KafkaSender kafkaSender;

    public DialogSender(KafkaSender kafkaSender) {
        this.kafkaSender = kafkaSender;
    }

    public void send(StatSend stat){
        kafkaSender.sendMessage(tgStatTopic, stat);
        log.info(String.format("Statistic for id %d send", stat.catId()));
    }
}
