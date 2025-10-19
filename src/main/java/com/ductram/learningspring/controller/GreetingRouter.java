package com.ductram.learningspring.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class GreetingRouter {

    @Bean
    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions.route()
                .GET("/hello", request -> {
                    String name = request.queryParam("name").orElse("World");
                    return ServerResponse.ok().bodyValue("Hello, " + name);
                })
                .build();
    }
}
