package com.littlepay.farecalculator.common;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class CSVReaderTest {

    @Test
    void reader_success() throws FileNotFoundException {
        String filePath = "C:\\Users\\samhita\\Downloads\\fare-calculator\\src\\main\\resources\\taps.csv";
        CSVReader csvReader = Mockito.mock(CSVReader.class);
        BufferedReader bufferedReader = Mockito.mock(BufferedReader.class);
        Mockito.when(csvReader.getReader(filePath)).thenReturn(bufferedReader);
        assertNotNull(bufferedReader);
    }

    @Test
    void reader_file_not_found() throws FileNotFoundException {
        // Given
        String filePath = "C:\\does\\not\\exist\\taps.csv";
        CSVReader csvReader = new CSVReader();
        // When
        Exception exception = assertThrows(FileNotFoundException.class, () ->
                csvReader.getReader(filePath));
        //Then
        assertEquals("C:\\does\\not\\exist\\taps.csv (The system cannot find the path specified)", exception.getMessage());
    }

}
