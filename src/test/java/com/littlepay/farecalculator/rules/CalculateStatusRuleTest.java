package com.littlepay.farecalculator.rules;

import com.littlepay.farecalculator.dto.TapOnOffDTO;
import com.littlepay.farecalculator.dto.Taps;
import com.littlepay.farecalculator.enums.Rule;
import com.littlepay.farecalculator.enums.TripStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class CalculateStatusRuleTest {
    static TapOnOffDTO tapOnOffDTO;
    static Taps tapOn;
    static Taps tapOff;
    static DateTimeFormatter formatter;
    static CalculateStatusRule calculateStatusRule;

    @BeforeAll
    public static void init() {
        tapOnOffDTO = new TapOnOffDTO();
        tapOn = new Taps();
        tapOff = new Taps();
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        calculateStatusRule = new CalculateStatusRule();
    }

    @Test
    public void runRule_complete() {
        Taps tapOn = new Taps("", null, "ON", "Stop1", "", "", "123");
        Taps tapOff = new Taps("", null, "OFF", "Stop2", "", "", "123");
        //When
        TapOnOffDTO tapOnOffDTO = new TapOnOffDTO();
        tapOnOffDTO.setTapOn(tapOn);
        tapOnOffDTO.setTapOff(tapOff);

        Map<Rule, Object> expectedValue = new HashMap<>();
        expectedValue.put(Rule.STATUS, TripStatus.COMPLETE);
        Assertions.assertEquals(expectedValue, calculateStatusRule.runRule(tapOnOffDTO));
    }

    @Test
    public void runRule_cancelled() {
        Taps tapOn = new Taps("", null, "ON", "Stop1", "", "", "123");
        Taps tapOff = new Taps("", null, "OFF", "Stop1", "", "", "123");
        //When
        TapOnOffDTO tapOnOffDTO = new TapOnOffDTO();
        tapOnOffDTO.setTapOn(tapOn);
        tapOnOffDTO.setTapOff(tapOff);

        Map<Rule, Object> expectedValue = new HashMap<>();
        expectedValue.put(Rule.STATUS, TripStatus.CANCELLED);
        Assertions.assertEquals(expectedValue, calculateStatusRule.runRule(tapOnOffDTO));
    }

    @Test
    public void runRule_incomplete() {
        Taps tapOn = new Taps("", null, "ON", "Stop1", "", "", "123");
        //When
        TapOnOffDTO tapOnOffDTO = new TapOnOffDTO();
        tapOnOffDTO.setTapOn(tapOn);

        Map<Rule, Object> expectedValue = new HashMap<>();
        expectedValue.put(Rule.STATUS, TripStatus.INCOMPLETE);
        Assertions.assertEquals(expectedValue, calculateStatusRule.runRule(tapOnOffDTO));
    }

    @Test
    public void getTripStatus_complete() {
        Taps tapOn = new Taps("", null, "ON", "Stop1", "", "", "123");
        Taps tapOff = new Taps("", null, "OFF", "Stop2", "", "", "123");

        Assertions.assertEquals(TripStatus.COMPLETE, calculateStatusRule.getTripStatus(tapOn,tapOff));
    }

    @Test
    public void getTripStatus_cancelled() {
        Taps tapOn = new Taps("", null, "ON", "Stop1", "", "", "123");
        Taps tapOff = new Taps("", null, "OFF", "Stop1", "", "", "123");

        Assertions.assertEquals(TripStatus.CANCELLED, calculateStatusRule.getTripStatus(tapOn,tapOff));
    }

    @Test
    public void getTripStatus_incomplete() {
        Taps tapOn = new Taps("", null, "ON", "Stop1", "", "", "123");

        Assertions.assertEquals(TripStatus.INCOMPLETE, calculateStatusRule.getTripStatus(tapOn,null));
    }

    @AfterAll
    public static void destroy() {
        tapOnOffDTO = null;
        tapOn = null;
        tapOff = null;
        formatter = null;
        calculateStatusRule = null;
    }
}
