package com.hstat.tgb.dev;

import com.hstat.common.dtoModels.StatSend;
import com.hstat.tgb.kafka.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

/**
 * Developing services
 */

@RestController
@RequestMapping("/dev")
public class testController {

    @Value("${topic.name}")
    private String tgStatTopic;
    private final KafkaSender kafkaSender;
    @Autowired
    public testController(KafkaSender kafkaSender) {
        this.kafkaSender = kafkaSender;
    }

    @PostMapping("/test")
    public String test(){
        System.out.println("Called TGB test");
        return "TGB works " + new Date();
    }
     @PostMapping("/kaf")
    public String test(@RequestBody StatSend statSend){
        System.out.println("Received statSend " + statSend);
        return kafkaSender.sendStatMessage(tgStatTopic, statSend);
    }


}
