package com.littlepay.farecalculator.rules;

import com.littlepay.farecalculator.dto.TapOnOffDTO;
import com.littlepay.farecalculator.dto.Taps;
import com.littlepay.farecalculator.enums.Rule;
import com.littlepay.farecalculator.enums.TripStatus;
import org.junit.jupiter.api.*;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CalculateFareRuleTest {
    TapOnOffDTO tapOnOffDTO;
    Taps tapOn;
    Taps tapOff;
    DateTimeFormatter formatter;
    CalculateFareRule calculateFareRule;

    @BeforeEach
    public void init() {
        Map<String, Double> stops = new HashMap<>();
        stops.put("Stop1Stop1", Double.valueOf(0));
        stops.put("Stop1Stop2", Double.valueOf(3.25));
        stops.put("Stop1Stop3", Double.valueOf(7.3));
        stops.put("Stop2Stop1", Double.valueOf(3.25));
        stops.put("Stop2Stop2", Double.valueOf(0));
        stops.put("Stop2Stop3", Double.valueOf(5.5));
        stops.put("Stop3Stop1", Double.valueOf(7.3));
        stops.put("Stop3Stop2", Double.valueOf(5.5));
        stops.put("Stop3Stop3", Double.valueOf(0));
        tapOnOffDTO = new TapOnOffDTO();
        tapOn = new Taps();
        tapOff = new Taps();
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        calculateFareRule = new CalculateFareRule(stops);
    }


    @Test
    public void getCostPerTrip_completeTrip() {
        Assertions.assertEquals(0, calculateFareRule.getCostForCompleteCancelledTrip("Stop1", "Stop1").get());
        Assertions.assertEquals(3.25, calculateFareRule.getCostForCompleteCancelledTrip("Stop1", "Stop2").get());
        Assertions.assertEquals(7.3, calculateFareRule.getCostForCompleteCancelledTrip("Stop1", "Stop3").get());
        Assertions.assertEquals(3.25, calculateFareRule.getCostForCompleteCancelledTrip("Stop2", "Stop1").get());
        Assertions.assertEquals(0, calculateFareRule.getCostForCompleteCancelledTrip("Stop2", "Stop2").get());
        Assertions.assertEquals(5.5, calculateFareRule.getCostForCompleteCancelledTrip("Stop2", "Stop3").get());
    }

    @Test
    public void getCostPerTrip_incompleteTrip() {
        Optional<Double> fareMax = calculateFareRule.getCostForIncompleteTrip("Stop2");

        Assertions.assertTrue(fareMax.isPresent());
        Assertions.assertEquals(5.5, fareMax.get());
    }

    @Test
    public void getCostPerTrip_incompleteTrip_invalidStop() {
        Optional<Double> fareMax = calculateFareRule.getCostForIncompleteTrip("Stop23445");
        Assertions.assertFalse(fareMax.isPresent());
    }

    @Test
    public void getCostPerTrip_incompleteTrip_null() {
        Optional<Double> fareMax = calculateFareRule.getCostForIncompleteTrip(null);
        Assertions.assertFalse(fareMax.isPresent());
    }

    @Test
    public void runRule_success_complete() {
        Taps tapOn = new Taps("", null, "ON", "Stop1", "", "", "123");
        Taps tapOff = new Taps("", null, "OFF", "Stop2", "", "", "123");
        //When
        TapOnOffDTO tapOnOffDTO = new TapOnOffDTO();
        tapOnOffDTO.setTapOn(tapOn);
        tapOnOffDTO.setTapOff(tapOff);
        tapOnOffDTO.setTripStatus(TripStatus.COMPLETE);
        Map<Rule, Object> expectedValue = new HashMap<>();
        expectedValue.put(Rule.FARE, 3.25);
        Assertions.assertEquals(expectedValue, calculateFareRule.runRule(tapOnOffDTO));
    }

    @Test
    public void runRule_success_cancelled() {
        Taps tapOn = new Taps("", null, "ON", "Stop1", "", "", "123");
        Taps tapOff = new Taps("", null, "OFF", "Stop1", "", "", "123");
        //When
        TapOnOffDTO tapOnOffDTO = new TapOnOffDTO();
        tapOnOffDTO.setTapOn(tapOn);
        tapOnOffDTO.setTapOff(tapOff);
        tapOnOffDTO.setTripStatus(TripStatus.CANCELLED);
        Map<Rule, Object> expectedValue = new HashMap<>();
        expectedValue.put(Rule.FARE, 0.0);
        Assertions.assertEquals(expectedValue, calculateFareRule.runRule(tapOnOffDTO));
    }

    @Test
    public void runRule_success_incomplete() {
        Taps tapOn = new Taps("", null, "ON", "Stop2", "", "", "123");
        //When
        TapOnOffDTO tapOnOffDTO = new TapOnOffDTO();
        tapOnOffDTO.setTapOn(tapOn);
        tapOnOffDTO.setTripStatus(TripStatus.INCOMPLETE);
        Map<Rule, Object> expectedValue = new HashMap<>();
        expectedValue.put(Rule.FARE, 5.5);
        Assertions.assertEquals(expectedValue, calculateFareRule.runRule(tapOnOffDTO));
    }

    @AfterEach
    public void destroy() {
        tapOnOffDTO = null;
        tapOn = null;
        tapOff = null;
        formatter = null;
        calculateFareRule = null;
    }
}
