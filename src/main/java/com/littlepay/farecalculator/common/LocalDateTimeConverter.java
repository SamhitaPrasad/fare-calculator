package com.littlepay.farecalculator.common;

import com.opencsv.bean.AbstractBeanField;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.littlepay.farecalculator.common.Constants.DD_MM_YYYY_HH_MM_SS;

public class LocalDateTimeConverter extends AbstractBeanField {



    public LocalDateTimeConverter() {
    }

    @Override
    protected Object convert(String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DD_MM_YYYY_HH_MM_SS);
        return LocalDateTime.parse(value.trim(), formatter);
    }

}
