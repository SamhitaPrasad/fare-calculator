package com.littlepay.farecalculator.parser;

import com.littlepay.farecalculator.common.Util;
import com.littlepay.farecalculator.dto.Taps;
import com.littlepay.farecalculator.exception.EmptyCSVException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.io.Reader;
import java.util.List;

@Component
public class CSVParser {

    Logger logger = LoggerFactory.getLogger(CSVParser.class);

    public List<Taps> parse(Reader reader) throws EmptyCSVException {

        logger.info("Parsing the csv");
        CsvToBean<Taps> csvToBean = new CsvToBeanBuilder(reader)
                .withType(Taps.class)
                .withSeparator(',')
                .withIgnoreLeadingWhiteSpace(true)
                .withIgnoreEmptyLine(true)
                .build();
        List<Taps> results = csvToBean.parse();
        //If an invalid file is parsed then the resulting bean will have null values
        if(results.size() == 0 || Util.isBeanEmpty(results.get(0))){
            throw new EmptyCSVException("The file used for upload may be corrupted.");
        }
        logger.info("Parsing completed: {}", results.size());
        return results;
    }

}
