package com.littlepay.farecalculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FareCalculatorApplication {

    static final  Logger LOGGER = LoggerFactory.getLogger(FareCalculatorApplication.class);

    //This is the entry point to the application
    public static void main(String[] args) {
        SpringApplication.run(FareCalculatorApplication.class, args);
    }
}
