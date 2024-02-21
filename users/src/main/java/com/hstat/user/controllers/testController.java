package com.hstat.user.controllers;

import com.hstat.common.dtoModels.TgMessage;
import com.hstat.user.kafka.KafkaSender;
import com.hstat.user.model.User;
import com.hstat.user.services.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user/test")
public class testController {

    private final UserServiceImpl userService;
    private final KafkaSender kafkaSender;

    public testController(UserServiceImpl userService, KafkaSender kafkaSender) {
        this.userService = userService;
        this.kafkaSender = kafkaSender;
    }

    @PostMapping("/new")
    public Long saveUserTest(@RequestBody User user){
        log.info(String.format("User %s received", user));
        return userService.saveUser(user);
    }

    @PostMapping("/selftest")
    public void selfTest(@RequestBody Long id){
        kafkaSender.sendKafkaTg(new TgMessage(id, "User Service received a message via REST"));
        log.info(String.format("Received test request for id %d", id));
    }
}
