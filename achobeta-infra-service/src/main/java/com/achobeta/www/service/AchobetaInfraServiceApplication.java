package com.achobeta.www.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AchobetaInfraServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AchobetaInfraServiceApplication.class, args);
    }

}
