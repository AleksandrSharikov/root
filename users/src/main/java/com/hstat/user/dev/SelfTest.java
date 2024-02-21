package com.hstat.user.dev;

import com.hstat.common.dtoModels.TgMessage;
import com.hstat.user.kafka.KafkaSender;
import org.springframework.stereotype.Service;

@Service
public class SelfTest {

    private final KafkaSender kafkaSender;
    private int counter = 0;

    public SelfTest(KafkaSender kafkaSender) {
        this.kafkaSender = kafkaSender;
    }

    public void test(long id){
        kafkaSender.sendKafkaTg(new TgMessage(id, String.format("User service receives a message via Kafka %d", counter++) ));
    }
}
