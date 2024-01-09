package com.hstat.tgb.dev;

import com.hstat.tgb.dto.StatSend;
import com.hstat.tgb.kafka.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/dev")
public class testController {

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
        return kafkaSender.sendMessage(statSend);
    }


}
