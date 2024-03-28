package com.rex.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextStartedEvent;

import java.util.TimeZone;

@SpringBootApplication
public class JavaToolApplication {
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication app = new SpringApplication(JavaToolApplication.class);
        ApplicationContext act = app.run(args);
        act.publishEvent(new ContextStartedEvent(act));
    }
}
