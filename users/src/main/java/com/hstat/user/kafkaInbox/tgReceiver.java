package com.hstat.user.kafkaInbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hstat.common.dtoModels.UserCard;
import com.hstat.user.model.User;
import com.hstat.user.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class tgReceiver {
    private final String userCardTopic = "tg.userReg";
    private final ObjectMapper objectMapper;
    private final UserService userService;

    public tgReceiver(ObjectMapper objectMapper, UserService userService) {
        this.objectMapper = objectMapper;
        this.userService = userService;
    }

    @KafkaListener(id = "UserCardReceiver", topics = userCardTopic)
    public void receiveUserCard(String message) throws JsonProcessingException {
        UserCard card = objectMapper.readValue(message, UserCard.class);
        long id = userService.saveUser(new User(card));
        log.info(String.format("Received and user %s and saved with id %d",
                card, id));
    }
}
