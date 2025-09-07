package com.ductram.learningspring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class Home {

    @GetMapping
    public String home() {
        log.info("Thread ID = {}", Thread.currentThread().getId());
        return "Hello, this is my Spring Boot Application";
    }

    @GetMapping("/address")
    public String address() {
        log.info("Thread ID = {}", Thread.currentThread().getId());
        return "This is address endpoint";
    }
}
