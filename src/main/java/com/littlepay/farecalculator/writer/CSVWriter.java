package com.littlepay.farecalculator.writer;

import com.littlepay.farecalculator.dto.CSVOutput;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import java.util.List;

public class CSVWriter {
    public void output(List<CSVOutput> csvOutput) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        String fileName = "F:\\development\\poc\\fare-calculator-poc\\fare-calculator\\src\\main\\resources\\output\\output.csv";
        Writer writer = new FileWriter(fileName);
        StatefulBeanToCsvBuilder<CSVOutput> builder = new StatefulBeanToCsvBuilder<>(writer);
        StatefulBeanToCsv<CSVOutput> beanWriter = builder.build();
        beanWriter.write(csvOutput);
        writer.close();
    }
}
