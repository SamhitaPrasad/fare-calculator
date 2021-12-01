package com.littlepay.farecalculator.parser;

import com.littlepay.farecalculator.dto.Taps;

import com.littlepay.farecalculator.exception.EmptyCSVException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CSVParserTests {

    @Test
    void parse_csv_success() throws FileNotFoundException, EmptyCSVException {
        List<Taps> result = new ArrayList<>();
        Reader csvReader = Mockito.mock(Reader.class);
        Taps t = new Taps("", null,"","","","","");
        result.add(t);
        CSVParser csvParser = Mockito.mock(CSVParser.class);
        Mockito.when(csvParser.parse(csvReader)).thenReturn(result);
        assertEquals(1, result.size());
    }

}
