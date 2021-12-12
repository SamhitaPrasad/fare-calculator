package com.littlepay.farecalculator.writer;

import com.littlepay.farecalculator.common.Constants;
import com.littlepay.farecalculator.dto.CSVOutput;
import com.littlepay.farecalculator.exception.CSVCreationException;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class CSVWriter {

    public static final File file = new File(Constants.C_TMP);
    private StringBuilder stringBuilder = new StringBuilder();
    private Writer writer;
    private FileWriter fileWriter;
    static final Logger LOGGER = LoggerFactory.getLogger(CSVWriter.class);

    public void output(List<CSVOutput> csvOutput) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, URISyntaxException, CSVCreationException {
        createDirectory();
        try {
            fileWriter = new FileWriter(checkIfFileExists(getNewFilename()));
        } catch (IOException e) {
            LOGGER.error("Error creating file: {}",e.getMessage());
        }
        writer  = fileWriter;
        StatefulBeanToCsvBuilder<CSVOutput> builder = new StatefulBeanToCsvBuilder<>(writer);
        StatefulBeanToCsv<CSVOutput> beanWriter = builder.build();
        beanWriter.write(csvOutput);
        try {
            writer.close();
        } catch (IOException e) {
            LOGGER.error("Error writing file: {}",e.getMessage());
        }
    }


    public String checkIfFileExists(String filename) throws CSVCreationException {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
               throw new CSVCreationException("Error creating new file");
            }
        }
        return filename;
    }

    private String getNewFilename() {
        stringBuilder.delete(0, stringBuilder.length());
        stringBuilder.append(getString());
        return stringBuilder.toString();
    }

    private String getString() {
        return file.getAbsolutePath()+"\\"+ Constants.FILENAME+new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS).format(new Date())+Constants.EXTENSION;
    }

    public void createDirectory(){
        File theDir = new File(Constants.C_TMP);
        if (!theDir.exists()){
            theDir.mkdirs();
        }
    }
}
