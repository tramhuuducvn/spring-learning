package com.ductram.learningspring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    @GetMapping
    public String home(@RequestParam String name) throws InterruptedException {
        Thread.sleep(50);
        return String.format("Hello, %s!", name);
    }
}
