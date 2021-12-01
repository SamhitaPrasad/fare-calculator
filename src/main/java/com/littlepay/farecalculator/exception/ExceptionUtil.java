package com.littlepay.farecalculator.exception;

import com.littlepay.farecalculator.dto.CSVOutput;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExceptionUtil {

    public List<ExceptionMessage> exceptionMessages;
    public List<SimpleExceptionMessage> simpleExceptionMessage;

    public List<SimpleExceptionMessage> exceptionMessage(String message){
        simpleExceptionMessage = new ArrayList<>();
        simpleExceptionMessage.add(new SimpleExceptionMessage(message));
        return simpleExceptionMessage;
    }

    public List<ExceptionMessage> exceptionMessage(String message, List<CSVOutput> csvOutputList){
        exceptionMessages = new ArrayList<>();
        exceptionMessages.add(new ExceptionMessage(message, csvOutputList));
        return exceptionMessages;
    }


}
