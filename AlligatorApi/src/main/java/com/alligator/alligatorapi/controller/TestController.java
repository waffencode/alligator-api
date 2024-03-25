package com.alligator.alligatorapi.controller;

import com.alligator.alligatorapi.entity.user.User;
import com.alligator.alligatorapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class TestController {
    private final UserService userService;

    @GetMapping("/onlyforauthenticated")
    public String helloWorld() {
        User user;
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Logger logger = Logger.getLogger(TestController.class.getName());
        logger.log(Level.INFO, "asdasdasdasdasdassda");

        user = userService.loadFromDatabase(username);

        return "Hello, " + user.getUsername() + "!";
    }
}
