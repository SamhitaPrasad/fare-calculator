package com.littlepay.farecalculator.writer;

import com.littlepay.farecalculator.dto.CSVOutput;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CSVWriter {

    public final String FILENAME = "output";
    public final String EXTENSION = ".csv";
    public File file = new File("C:/tmp");
    Logger logger = LoggerFactory.getLogger(CSVWriter.class);


    public void output(List<CSVOutput> csvOutput) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, URISyntaxException {

        createDirectory();
        Writer writer = new FileWriter(getFileName());
        StatefulBeanToCsvBuilder<CSVOutput> builder = new StatefulBeanToCsvBuilder<>(writer);
        StatefulBeanToCsv<CSVOutput> beanWriter = builder.build();
        beanWriter.write(csvOutput);
        writer.close();
    }


    public String getFileName(){
        String filename = file.getAbsolutePath()+"\\"+FILENAME+new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date())+EXTENSION;
        file = new File(filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filename;
    }

    public void createDirectory(){
        File theDir = new File("C:/tmp");
        if (!theDir.exists()){
            theDir.mkdirs();
        }
    }
}
