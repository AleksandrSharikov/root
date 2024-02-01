package com.hstat.tgb.dialogPostProcessors;

import com.hstat.common.dtoModels.UserCard;
import com.hstat.tgb.dialogInterface.ResultProcessor;
import com.hstat.tgb.kafka.KafkaSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RegPostProcessor implements ResultProcessor<UserCard> {
    private final KafkaSender kafkaSender;

    public RegPostProcessor(KafkaSender kafkaSender) {
        this.kafkaSender = kafkaSender;
    }

    @Override
    public void process(UserCard data) {
            log.info(String.format("prepared user data %s", data));
            kafkaSender.sendKafkaUserCard(data);

    }
}
