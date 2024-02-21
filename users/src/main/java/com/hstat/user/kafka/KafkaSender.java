package com.hstat.user.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hstat.common.CommonConstants;
import com.hstat.common.dtoModels.DTO;
import com.hstat.common.dtoModels.StatSend;
import com.hstat.common.dtoModels.TgMessage;
import com.hstat.common.dtoModels.UserCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Kafka message sender
 */

@Component
public class KafkaSender {

        private final ObjectMapper objectMapper;
        private final KafkaTemplate<String, String> kafkaTemplate;
        private final String tgSend = CommonConstants.TopicNames.BOT_OUT.getName();

        @Autowired
        public KafkaSender(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
            this.kafkaTemplate = kafkaTemplate;
            this.objectMapper = objectMapper;
        }

    public void sendKafkaTg(TgMessage message) {
        sendKafka (tgSend, message);
    }



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
