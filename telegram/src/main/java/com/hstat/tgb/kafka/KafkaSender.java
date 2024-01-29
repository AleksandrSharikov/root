package com.hstat.tgb.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hstat.common.dtoModels.DTO;
import com.hstat.common.dtoModels.StatSend;
import com.hstat.common.dtoModels.TgMessage;
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
        private String tgSend = "tg.send";

        private final ObjectMapper objectMapper;
        private final KafkaTemplate<String, String> kafkaTemplate;

        @Autowired
        public KafkaSender(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
            this.kafkaTemplate = kafkaTemplate;
            this.objectMapper = objectMapper;
        }

    public String sendStatMessage(String topicName, StatSend statSend) {
            return sendKafka (topicName, statSend);
    }
    public void sendKafkaTg(TgMessage message) {
            sendKafka (tgSend, message);
    }

        // Send message to  the only topic
  /*      public String sendStatMessage(String topicName, StatSend statSend){
            try {
                String statAsMessage = objectMapper.writeValueAsString(statSend);
                kafkaTemplate.send(topicName, statAsMessage);
            }
            catch (JsonProcessingException e){
                System.out.println("Kafka sender threw Json Processing Exception");
                return "Kafka sender threw Json Processing Exception";
            }

            return "message sent";
        }

*/
    private <T extends DTO> String sendKafka(String topicName, T parcel){
        try {
            String toSend = objectMapper.writeValueAsString(parcel);
            kafkaTemplate.send(topicName, toSend);
        }
        catch (JsonProcessingException e){
            System.out.println("Kafka sender threw Json Processing Exception");
            return "Kafka sender threw Json Processing Exception";
        }

        return "message sent";
    }
}
