package com.littlepay.farecalculator.exception;

import com.littlepay.farecalculator.dto.Taps;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class ExceptionHelperTest {
    static EmptyCSVException emptyCSVException;
    static List<ExceptionMessage> exceptionMessageList;
    static List<ExceptionMessage> expectedExceptionMessageListWithCsv;
    static ExceptionHelper exceptionHelper;
    static ConstraintViolationException constraintViolationException;
    static Set<ConstraintViolation<Taps>> constraintViolationSet;
    static CSVCreationException csvCreationException;

    @BeforeAll
    public static void init() {
        emptyCSVException = new EmptyCSVException("Exception");
        exceptionMessageList = Arrays.asList(new ExceptionMessage("Exception"));
        expectedExceptionMessageListWithCsv = Arrays.asList(new ExceptionMessage("Exception", new ArrayList<>(Arrays.asList())));
        exceptionHelper = new ExceptionHelper();
        constraintViolationSet = new HashSet<>();
        constraintViolationException = new ConstraintViolationException("Exception", constraintViolationSet);
        csvCreationException = new CSVCreationException("Exception", new ArrayList<>(Arrays.asList()));
    }

    @Test
    public void handle_empty_csv_exception_test() {
        ResponseEntity<List<?>> responseEntity = exceptionHelper.handleEmptyCSVException(emptyCSVException);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Assertions.assertEquals(exceptionMessageList.get(0).toString(), responseEntity.getBody().get(0).toString());
    }

    @Test
    public void handle_constraint_violation_exception_test() {

        ResponseEntity<List<?>> responseEntity = exceptionHelper.handleConstraintViolationException(constraintViolationException);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Assertions.assertEquals(exceptionMessageList.get(0).toString(), responseEntity.getBody().get(0).toString());
    }

    @Test
    public void handle_csv_Creation_exception_test() {
        ResponseEntity<List<?>> responseEntity = exceptionHelper.handleCSVCreationException(csvCreationException);
        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        Assertions.assertEquals(expectedExceptionMessageListWithCsv.get(0).toString(), responseEntity.getBody().get(0).toString());
    }

    @AfterAll
    public static void destroy() {
        emptyCSVException = null;
        exceptionMessageList = null;
        expectedExceptionMessageListWithCsv = null;
        exceptionHelper = null;
        constraintViolationSet = null;
        constraintViolationException = null;
        csvCreationException = null;
    }
}
