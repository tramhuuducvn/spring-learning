package com.ductram.learningspring.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.application")
public class ApplicationProperties {

    private String appName;
    private String author;
    private int old;

    @Override
    public String toString() {
        return "ApplicationProperties [appName=" + appName + ", author=" + author + ", old=" + old + "]";
    }
}
