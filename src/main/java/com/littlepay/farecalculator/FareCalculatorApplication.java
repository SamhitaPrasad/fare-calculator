package com.littlepay.farecalculator;

import com.littlepay.farecalculator.config.YAMLConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//TODO: remove command line runner and its overriding methods
@SpringBootApplication
public class FareCalculatorApplication {

    private static Logger LOGGER = LoggerFactory.getLogger(FareCalculatorApplication.class);

    //This is the entry point to the application
    public static void main(String[] args) {
        SpringApplication.run(FareCalculatorApplication.class, args);
    }
}
