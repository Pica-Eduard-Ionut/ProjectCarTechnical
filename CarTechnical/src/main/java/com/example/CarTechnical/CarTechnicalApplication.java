package com.example.CarTechnical;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CarTechnicalApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarTechnicalApplication.class, args);
    }
}
