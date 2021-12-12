package com.littlepay.farecalculator.config;

import com.littlepay.farecalculator.FareCalculatorApplication;

import com.littlepay.farecalculator.rules.CalculateFareRule;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = FareCalculatorApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"spring.profiles.active = test"})
class YAMLIntegrationTest {

    @Autowired
    private YAMLConfig config;

    @Autowired
    private CalculateFareRule calculateFareRule;

    @Test
    void getStops_success() {
        assertEquals(getExpectedStops(), config.getStops());
    }
    
    @Test
    void getStops_calculateFare() {
        assertEquals(getExpectedStops(), calculateFareRule.getStops());
    }

    private Map<String, Double> getExpectedStops() {
        Map<String, Double> expectedStops = new HashMap<>();
        expectedStops.put("Stop1Stop1", Double.valueOf(0));
        expectedStops.put("Stop1Stop2", Double.valueOf(3.25));
        expectedStops.put("Stop1Stop3", Double.valueOf(7.3));
        expectedStops.put("Stop2Stop1", Double.valueOf(3.25));
        expectedStops.put("Stop2Stop2", Double.valueOf(0));
        expectedStops.put("Stop2Stop3", Double.valueOf(5.5));
        expectedStops.put("Stop3Stop1", Double.valueOf(7.3));
        expectedStops.put("Stop3Stop2", Double.valueOf(5.5));
        expectedStops.put("Stop3Stop3", Double.valueOf(0));
        return expectedStops;
    }
}
