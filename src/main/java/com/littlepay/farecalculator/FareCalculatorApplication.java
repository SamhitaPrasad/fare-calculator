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
public class FareCalculatorApplication implements CommandLineRunner {

    private static Logger LOGGER = LoggerFactory.getLogger(FareCalculatorApplication.class);


    @Autowired
    private YAMLConfig myConfig;

    //This is the entry point to the application
    public static void main(String[] args) {
        SpringApplication.run(FareCalculatorApplication.class, args);
    }

    @Override
    public void run(String... args) {

        System.out.println("using maatrix:" + myConfig.getMatrix());
        System.out.println("using environment:" + myConfig.getEnvironment());
        System.out.println("name:" + myConfig.getName());
        System.out.println("enabled:" + myConfig.isEnabled());
        System.out.println("stops:" + myConfig.getStops());

    }


}
