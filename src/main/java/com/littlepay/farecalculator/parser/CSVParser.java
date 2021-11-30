package com.littlepay.farecalculator.parser;

import com.littlepay.farecalculator.dto.Taps;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.util.List;

public class CSVParser {

    Logger logger = LoggerFactory.getLogger(CSVParser.class);

    public List<Taps> parse(Reader reader) {

        logger.info("Parsing the csv");
        CsvToBean<Taps> csvToBean = new CsvToBeanBuilder(reader)
                .withType(Taps.class)
                .withSeparator(',')
                .withIgnoreLeadingWhiteSpace(true)
                .withIgnoreEmptyLine(true)
                .build();
        List<Taps> results = csvToBean.parse();
        logger.info("Parsing completed: {}", results.size());
        return results;
    }

}
