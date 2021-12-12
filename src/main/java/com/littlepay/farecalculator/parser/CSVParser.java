package com.littlepay.farecalculator.parser;

import com.littlepay.farecalculator.dto.Taps;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.io.Reader;
import java.util.List;

@Component
@Validated
public class CSVParser {

    static final Logger LOGGER = LoggerFactory.getLogger(CSVParser.class);

    public List<@Valid Taps> parse(Reader reader) throws ConstraintViolationException {

        LOGGER.info("Parsing the csv");
        CsvToBean<Taps> csvToBean = new CsvToBeanBuilder(reader)
                .withType(Taps.class)
                .withSeparator(',')
                .withIgnoreLeadingWhiteSpace(true)
                .withIgnoreEmptyLine(true)
                .build();
        List<Taps> results = csvToBean.parse();
        LOGGER.info("Parsing completed: {}", results.size());
        return results;
    }

}
