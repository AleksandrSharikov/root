package com.hstat.user.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hstat.dtoModels.tgMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Kafka message sender
 */

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

        // Send message to  the only topic
        public String sendMessage(String topicName, tgMessage message){
            try {
                String statAsMessage = objectMapper.writeValueAsString(message);
                kafkaTemplate.send(topicName, statAsMessage);
            }
            catch (JsonProcessingException e){
                System.out.println("Kafka sender threw Json Processing Exception");
                return "Kafka sender threw Json Processing Exception";
            }

            return "message sent";
        }

}
