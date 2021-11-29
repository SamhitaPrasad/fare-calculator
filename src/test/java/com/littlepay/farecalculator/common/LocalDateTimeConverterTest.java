package com.littlepay.farecalculator.common;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class LocalDateTimeConverterTest {

    static LocalDateTimeConverter localDateTimeConverter;

    @BeforeAll
    public static void init() {
        localDateTimeConverter = new LocalDateTimeConverter();
    }

    @Test
    public void time_convert_success() {
        String timeToBeParsed = "28-11-2021 13:05:00";
        String expectedTimeAfterParsing = "2021-11-28T13:05";
        LocalDateTime expectedLocalDateTime = (LocalDateTime) localDateTimeConverter.convert(timeToBeParsed);
        Assertions.assertEquals(expectedLocalDateTime.toString(), expectedTimeAfterParsing);
    }

    @Test
    public void time_convert_success_untrimmed_values() {
        String timeToBeParsed = " 28-11-2021 13:05:00";
        LocalDateTime expectedLocalDateTime = (LocalDateTime) localDateTimeConverter.convert(timeToBeParsed);
        String expectedParsedValue = "2021-11-28T13:05";
        Assertions.assertEquals(expectedLocalDateTime.toString(), expectedParsedValue);
    }

    @Test
    public void time_convert_fail_parse_exception() {
        String time = "28-Nov-2021 13:05:00";
        Assertions.assertThrows(DateTimeParseException.class, () ->
                localDateTimeConverter.convert(time)
        );
    }

    @AfterAll
    public static void destroy(){
        localDateTimeConverter = null;
    }

}
