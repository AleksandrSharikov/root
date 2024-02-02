package com.bot.kafka;

import com.bot.processor.OutcomeProcessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hstat.common.dtoModels.TgMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaReceiver {

    private static final String tgSendTopic = "tg.send";
    private final ObjectMapper objectMapper;
    private final OutcomeProcessor outcomeProcessor;
    @Autowired
    public KafkaReceiver(ObjectMapper objectMapper, OutcomeProcessor outcomeProcessor) {
        this.objectMapper = objectMapper;
        this.outcomeProcessor = outcomeProcessor;
    }

    @KafkaListener(id = "TgListener", topics = tgSendTopic)
    public void consumeMessage(String message) throws JsonProcessingException {

        TgMessage received = objectMapper.readValue(message, TgMessage.class);
        log.info(String.format("Received message \"%s\" to send", received.message()));
        outcomeProcessor.sendMessage(received);
    }
}