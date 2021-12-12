package com.littlepay.farecalculator.service;

import com.littlepay.farecalculator.FareCalculatorApplication;
import com.littlepay.farecalculator.dto.Taps;
import com.littlepay.farecalculator.parser.CSVParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

@SpringBootTest(classes = FareCalculatorApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AggregatorServiceIntegrationTest {

    @Autowired
    CSVParser csvParser;

    @Test
    public void readAndParse_constraintValidation_integration() throws URISyntaxException, IOException {
        //Given - if you send a multipart file, it returns a list of Taps
        Reader reader = new InputStreamReader(file().getInputStream());

        Assertions.assertThrows(ConstraintViolationException.class,() -> csvParser.parse(reader));
    }

    private FileSystemResource file() throws URISyntaxException {
        return new FileSystemResource(Paths.get(ClassLoader.getSystemResource("taps.invalid.csv").toURI()));
    }

}
