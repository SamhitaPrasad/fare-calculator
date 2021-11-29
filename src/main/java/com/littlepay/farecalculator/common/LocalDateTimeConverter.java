package com.littlepay.farecalculator.common;

import com.opencsv.bean.AbstractBeanField;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter extends AbstractBeanField {

    public LocalDateTimeConverter() {
    }

    @Override
    protected Object convert(String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return LocalDateTime.parse(value.trim(), formatter);
    }

}
