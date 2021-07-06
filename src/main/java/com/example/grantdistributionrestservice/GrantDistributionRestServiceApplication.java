package com.example.grantdistributionrestservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class GrantDistributionRestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrantDistributionRestServiceApplication.class, args);
        log.info("Application started successfully!");
    }

}
