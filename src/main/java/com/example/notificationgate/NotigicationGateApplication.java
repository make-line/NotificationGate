package com.example.notificationgate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NotigicationGateApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotigicationGateApplication.class, args);
    }

}
