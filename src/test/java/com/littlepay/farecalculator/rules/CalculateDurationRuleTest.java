package com.littlepay.farecalculator.rules;

import com.littlepay.farecalculator.dto.TapOnOffDTO;
import com.littlepay.farecalculator.dto.Taps;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CalculateDurationRuleTest {
    static TapOnOffDTO tapOnOffDTO;
    static Taps tapOn;
    static Taps tapOff;
    static DateTimeFormatter formatter;
    static CalculateDurationRule calculateDurationRule;

    @BeforeAll
    public static void init() {
        tapOnOffDTO = new TapOnOffDTO();
        tapOn = new Taps();
        tapOff = new Taps();
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        calculateDurationRule = new CalculateDurationRule();
    }

    @Test
    public void getTimeDifferenceInMillis_success() {
        //Given
        tapOff.setDateTimeUTC(LocalDateTime.parse("28-11-2021 13:05:50", formatter));
        tapOn.setDateTimeUTC(LocalDateTime.parse("28-11-2021 13:05:00", formatter));
        tapOnOffDTO.setTapOff(tapOff);
        tapOnOffDTO.setTapOn(tapOn);
        //When
        long expectedDifference = calculateDurationRule.getTimeDifferenceInMillis(tapOnOffDTO);
        //Then
        Assertions.assertEquals(50000,expectedDifference);

    }

    @Test
    public void getTimeDifferenceInMillis_null_should_return_zero() {
        //Given
        tapOn.setDateTimeUTC(LocalDateTime.parse("28-11-2021 13:05:00", formatter));
        tapOnOffDTO.setTapOn(tapOn);
        //When Then
        Assertions.assertEquals(0, calculateDurationRule.getTimeDifferenceInMillis(tapOnOffDTO));
    }

    @AfterAll
    public static void destroy() {
        tapOnOffDTO = null;
        tapOn = null;
        tapOff = null;
        formatter = null;
    }
}


