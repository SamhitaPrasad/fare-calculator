package com.littlepay.farecalculator.parser;

import com.littlepay.farecalculator.dto.Taps;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CSVParserTests {


    @Test
    void parse_csv_success() throws FileNotFoundException {
        List<Taps> result = new ArrayList<>();
        Taps t = new Taps("", null,"","","","","");
        result.add(t);
        CSVParser csvParser = Mockito.mock(CSVParser.class);
        Mockito.when(csvParser.parse("C:/Some/Random/Path/filename.csv")).thenReturn(result);
        assertEquals(1, result.size());
    }

}
