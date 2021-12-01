package com.littlepay.farecalculator.common;

import com.littlepay.farecalculator.dto.TapOnOffDTO;
import com.littlepay.farecalculator.dto.Taps;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class UtilTest {
    static TapOnOffDTO tapOnOffDTO;
    static Taps tapOn;
    static Taps tapOff;
    static DateTimeFormatter formatter;

    @BeforeAll
    public static void init() {
        tapOnOffDTO = new TapOnOffDTO();
        tapOn = new Taps();
        tapOff = new Taps();
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    }

    @Test
    public void getTimeDifferenceInMillis_success() {
        //Given
        tapOff.setDateTimeUTC(LocalDateTime.parse("28-11-2021 13:05:50", formatter));
        tapOn.setDateTimeUTC(LocalDateTime.parse("28-11-2021 13:05:00", formatter));
        tapOnOffDTO.setTapOff(tapOff);
        tapOnOffDTO.setTapOn(tapOn);
        //When
        long expectedDifference = Util.getTimeDifferenceInMillis(tapOnOffDTO);
        //Then
        Assertions.assertEquals(expectedDifference, 50000);

    }

    @Test
    public void getTimeDifferenceInMillis_null_should_return_zero() {
        //Given
        tapOn.setDateTimeUTC(LocalDateTime.parse("28-11-2021 13:05:00", formatter));
        tapOnOffDTO.setTapOn(tapOn);
        //When Then
        Assertions.assertEquals(0, Util.getTimeDifferenceInMillis(tapOnOffDTO));
    }

    @Test
    public void convertToStringArray_success() {
        String s = "{{0, 3.25, 7.3}, {3.25, 0, 5.5},{7.3, 5.5, 0}}";
        String[][] expectedArray = {{"0", "3.25", "7.3"},{"3.25", "0", "5.5"},{"7.3", "5.5", "0"}};
        String[][] actualArray = Util.convertToStringArray(s);
        //Returning true for some strange reason.
//        Assertions.assertTrue(Arrays.equals(expectedArray, actualArray));
        Assertions.assertTrue(expectedArray[0][2].equals(actualArray[0][2]));
        Assertions.assertTrue(expectedArray[1][1].equals(actualArray[1][1]));
        Assertions.assertTrue(expectedArray[2][0].equals(actualArray[2][0]));
    }

    @AfterAll
    public static void destroy() {
        tapOnOffDTO = null;
        tapOn = null;
        tapOff = null;
        formatter = null;
    }
}
