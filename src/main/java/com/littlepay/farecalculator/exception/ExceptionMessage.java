package com.littlepay.farecalculator.exception;

import com.littlepay.farecalculator.dto.CSVOutput;

import java.util.List;

public class ExceptionMessage {

    private String message;

    List<CSVOutput> csvOutputList;

    public ExceptionMessage(String message) {
        this.message = message;
    }

    public ExceptionMessage(String message, List<CSVOutput> csvOutputList) {
        this.message = message;
        this.csvOutputList = csvOutputList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CSVOutput> getCsvOutputList() {
        return csvOutputList;
    }

    public void setCsvOutputList(List<CSVOutput> csvOutputList) {
        this.csvOutputList = csvOutputList;
    }

    @Override
    public String toString() {
        return "ExceptionMessage{" +
                "message='" + message + '\'' +
                ", csvOutputList=" + csvOutputList +
                '}';
    }
}
