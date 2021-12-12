package com.littlepay.farecalculator.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;

@ControllerAdvice
public class ExceptionHelper {
    static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHelper.class);

    @ExceptionHandler (value = { EmptyCSVException.class })
    public ResponseEntity<List<?>> handleEmptyCSVException(EmptyCSVException exception){
        LOGGER.error("Error with CSV file.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionUtil.exceptionMessage(exception.getMessage()));
    }

    @ExceptionHandler (value = { ConstraintViolationException.class })
    public ResponseEntity<List<?>> handleConstraintViolationException(ConstraintViolationException exception){
        LOGGER.error("Error while validating beans.");
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionUtil.exceptionMessage(exception.getMessage()));
    }

    @ExceptionHandler (value = { CSVCreationException.class })
    public ResponseEntity<List<?>> handleCSVCreationException(CSVCreationException exception){
        LOGGER.error("Error creating csv output.");
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ExceptionUtil.exceptionMessage(exception.getMessage(), exception.csvOutput));
    }
}
