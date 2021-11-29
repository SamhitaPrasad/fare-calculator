package com.littlepay.farecalculator.parser;

import com.littlepay.farecalculator.common.CSVReader;
import com.littlepay.farecalculator.dto.Taps;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.util.List;

public class CSVParser {

    Logger logger = LoggerFactory.getLogger(CSVParser.class);

    @Autowired
    CSVReader csvReader;

    public List<Taps> parse(String filename) throws FileNotFoundException {
        Reader reader = csvReader.getReader(filename);
        CsvToBean<Taps> csvToBean = new CsvToBeanBuilder(reader)
                .withType(Taps.class)
                .withSeparator(',')
                .withIgnoreLeadingWhiteSpace(true)
                .withIgnoreEmptyLine(true)
                .build();
        List<Taps> results = csvToBean.parse();
        return results;
    }

}
