package com.littlepay.farecalculator.exception;

import com.littlepay.farecalculator.dto.CSVOutput;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//@Component
public class ExceptionUtil {

    public static List<ExceptionMessage> exceptionMessages;

    public ExceptionUtil(List<ExceptionMessage> exceptionMessages) {
        this.exceptionMessages = exceptionMessages;
    }

    public static List<ExceptionMessage> exceptionMessage(String message){
        exceptionMessages = new ArrayList<>();
        exceptionMessages.add(new ExceptionMessage(message));
        return exceptionMessages;
    }

    public static List<ExceptionMessage> exceptionMessage(String message, List<CSVOutput> csvOutputList){
        exceptionMessages = new ArrayList<>();
        exceptionMessages.add(new ExceptionMessage(message, csvOutputList));
        return exceptionMessages;
    }


}
