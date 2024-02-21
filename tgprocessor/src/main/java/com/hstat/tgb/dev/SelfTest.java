package com.hstat.tgb.dev;

import com.hstat.common.CommonConstants;
import com.hstat.common.dtoModels.TgMessage;
import com.hstat.common.dtoModels.UserCard;
import com.hstat.tgb.dialogInterface.ProcessorsList;
import com.hstat.tgb.kafka.KafkaSender;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SelfTest {
    private final KafkaSender kafkaSender;
    private final RestTemplate restTemplate;
    private final ProcessorsList processorsList;
    private final String url;

    public SelfTest(KafkaSender kafkaSender, RestTemplateBuilder restTemplateBuilder, ProcessorsList processorsList) {
        this.kafkaSender = kafkaSender;
        this.restTemplate = restTemplateBuilder.build();
        this.processorsList = processorsList;
        this.url = CommonConstants.ServicePort.USER.getPort();
    }

    public void test(long id){
        kafkaSender.sendKafkaUserCard(new UserCard(-5, id, "test message", null, null));
        restTemplate.postForObject(url + "/user/test/selftest", id, Long.class);
        kafkaSender.sendKafkaTg(new TgMessage(id, "TgProcessor received the message and send it to Stat and User services"));
        processorsList.getAllProcessors().forEach(s -> {
            System.out.println(s.getClass().getName());
        });
        System.out.println();
        processorsList.getAllMaps().forEach(s -> {
            System.out.println(s.getClass().getName());
        });
    }
}
