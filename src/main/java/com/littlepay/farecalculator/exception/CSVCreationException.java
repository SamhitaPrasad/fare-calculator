package com.littlepay.farecalculator.exception;

import com.littlepay.farecalculator.dto.CSVOutput;

import java.util.List;

public class CSVCreationException extends Exception{
    List<CSVOutput> csvOutput;

    public List<CSVOutput> getCsvOutput() {
        return csvOutput;
    }

    public void setCsvOutput(List<CSVOutput> csvOutput) {
        this.csvOutput = csvOutput;
    }

    public CSVCreationException(String message, List<CSVOutput> csvOutput) {
        super(message);
        this.csvOutput = csvOutput;
    }

    public CSVCreationException(String message) {
        super(message);
    }
}
