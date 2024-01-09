package com.hstat.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hstat.dto.StatSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TgbConsumer {

    private static final String orderTopic = "${topic.name}";
    private final ObjectMapper objectMapper;
    @Autowired
    public TgbConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = orderTopic)
    public void consumeMessage(String message) throws JsonProcessingException {

        StatSend received = objectMapper.readValue(message, StatSend.class);
        System.out.println(received);
    }

}