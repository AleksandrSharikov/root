package com.hstat.tgb.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hstat.tgb.dto.StatSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {

        @Value("${topic.name}")
        private String tgStatTopic;

        private final ObjectMapper objectMapper;
        private final KafkaTemplate<String, String> kafkaTemplate;

        @Autowired
        public KafkaSender(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
            this.kafkaTemplate = kafkaTemplate;
            this.objectMapper = objectMapper;
        }

        public String sendMessage(StatSend statSend){
            try {
                String statAsMessage = objectMapper.writeValueAsString(statSend);
                kafkaTemplate.send(tgStatTopic, statAsMessage);
            }
            catch (JsonProcessingException e){
                System.out.println("Kafka sender threw Json Processing Exception");
                return "Kafka sender threw Json Processing Exception";
            }

            return "message sent";
        }

}
