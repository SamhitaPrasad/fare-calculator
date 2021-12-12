package com.littlepay.farecalculator.config;

import com.littlepay.farecalculator.FareCalculatorApplication;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;
import java.util.Map;


@SpringBootTest(classes = FareCalculatorApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"spring.profiles.active = dev"})
class YAMLDevIntegrationTest {

    @Autowired
    private YAMLConfig config;

    @Test
    void whenProfileTest_thenNameTesting() {
        Map<String, Double> expectedStops = new HashMap<>();
        expectedStops.put("Stop1Stop1", Double.valueOf(0));
        Assertions.assertEquals(expectedStops, config.getStops());
    }
}
