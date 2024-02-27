package com.joranbergfeld.airportsystem.arrival;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ConfigurationPropertiesScan(basePackages = {"com.joranbergfeld.airportsystem.arrival"})
@SpringBootApplication
@EnableScheduling
public class ArrivalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArrivalApplication.class, args);
    }

}
