package com.littlepay.farecalculator.dto;

import com.littlepay.farecalculator.enums.TripStatus;

import com.littlepay.farecalculator.rules.CalculateStatusRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;

public class TapOnOffDTOTest {
    static TapOnOffDTO tapOnOffDTO;
    static CalculateStatusRule calculateStatusRule;
    static Taps tapOn;
    static Taps tapOff;
    static DateTimeFormatter formatter;

    @BeforeAll
    public static void init() {
        calculateStatusRule = new CalculateStatusRule();
        tapOnOffDTO = new TapOnOffDTO();
        tapOn = new Taps();
        tapOff = new Taps();
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    }


    @Test
    public void getTripStatus_missing_setters_expect_null() {
        //Given
        tapOn.setTapType("ON");
        tapOff.setTapType("OFF");
        tapOnOffDTO.setTapOn(tapOn);
        tapOnOffDTO.setTapOff(tapOff);
        tapOn.setStopId("Stop1");
//        tapOff.setStopId(null);
        //When Then
        Assertions.assertEquals(null, calculateStatusRule.getTripStatus(tapOnOffDTO.getTapOn(), tapOnOffDTO.getTapOff()));
    }

    @Test
    public void getTripStatus_completed_success() {
        //Given
        tapOn.setTapType("ON");
        tapOff.setTapType("OFF");
        //When
        tapOn.setStopId("Stop1");
        tapOff.setStopId("Stop3");
        tapOnOffDTO.setTapOn(tapOn);
        tapOnOffDTO.setTapOff(tapOff);
        //Then
        Assertions.assertEquals(TripStatus.COMPLETE, calculateStatusRule.getTripStatus(tapOnOffDTO.getTapOn(), tapOnOffDTO.getTapOff()));
    }

    @Test
    public void getTripStatus_incomplete_success() {
        //Given
        tapOn.setTapType("ON");
        //When
        tapOn.setStopId("Stop6");
        tapOnOffDTO.setTapOn(tapOn);
        tapOnOffDTO.setTapOff(null);
        //Then
        Assertions.assertEquals(TripStatus.INCOMPLETE, calculateStatusRule.getTripStatus(tapOnOffDTO.getTapOn(), tapOnOffDTO.getTapOff()));
    }
    
    @Test
    public void getTripStatus_cancelled_success() {
        //Given
        tapOn.setTapType("ON");
        tapOff.setTapType("OFF");
        //When
        tapOn.setStopId("Stop1");
        tapOff.setStopId("Stop1");
        tapOnOffDTO.setTapOn(tapOn);
        tapOnOffDTO.setTapOff(tapOff);
        //Then
        Assertions.assertEquals(TripStatus.CANCELLED, calculateStatusRule.getTripStatus(tapOnOffDTO.getTapOn(), tapOnOffDTO.getTapOff()));
    }


    @AfterAll
    public static void destroy() {
        calculateStatusRule = null;
        tapOnOffDTO = null;
        tapOn = null;
        tapOff = null;
        formatter = null;
    }
}
