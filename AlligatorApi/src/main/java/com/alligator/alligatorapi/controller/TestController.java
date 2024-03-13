package com.alligator.alligatorapi.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class TestController {

    @GetMapping
    public String helloWorld() {
        return "Hello!";
    }
}
