package com.littlepay.farecalculator.config;

import com.littlepay.farecalculator.FareCalculatorApplication;

import com.littlepay.farecalculator.rules.CalculateFareRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FareCalculatorApplication.class)
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
        expectedStops.put("Stop1Stop1",Double.valueOf(0));
        expectedStops.put("Stop1Stop2",Double.valueOf(3.25));
        expectedStops.put("Stop1Stop3",Double.valueOf(7.3));
        expectedStops.put("Stop2Stop1",Double.valueOf(3.25));
        expectedStops.put("Stop2Stop2",Double.valueOf(0));
        expectedStops.put("Stop2Stop3",Double.valueOf(5.5));
        expectedStops.put("Stop3Stop1",Double.valueOf(7.3));
        expectedStops.put("Stop3Stop2",Double.valueOf(5.5));
        expectedStops.put("Stop3Stop3",Double.valueOf(0));
        return expectedStops;
    }
}
