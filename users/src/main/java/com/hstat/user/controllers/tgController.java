package com.hstat.user.controllers;

import com.hstat.user.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tg")
public class tgController {

    private final UserService userService;

    public tgController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/check/{id}")
    public boolean present(@PathVariable long id){
        return userService.tgPresent(id);
    }
}
