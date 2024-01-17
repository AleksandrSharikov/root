package com.hstat.user.controllers;

import com.hstat.user.model.User;
import com.hstat.user.services.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user/test")
public class testController {

    private final UserServiceImpl userService;

    public testController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/new")
    public Long saveUserTest(@RequestBody User user){
        log.info(String.format("User %s received", user));
        return userService.saveUser(user);
    }

}
