package com.joranbergfeld.airportsystem.arrival;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan(basePackages = {"com.joranbergfeld.airportsystem.arrival"})
@SpringBootApplication
public class ArrivalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArrivalApplication.class, args);
    }

}
