package com.littlepay.farecalculator.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

public class CSVReader {

    Logger logger = LoggerFactory.getLogger(CSVReader.class);

    public Reader getReader(String filePath) throws FileNotFoundException {
        BufferedReader bufferedReader = null;
        FileReader fileReader = null;

        logger.info("Reading the CSV file");
        fileReader = new FileReader(filePath);
        bufferedReader = new BufferedReader(fileReader);
        return bufferedReader;
    }

    public boolean checkIfValid(String fileName) {
        try {
            File file = new File(fileName);
            return file.exists();
        } catch (Exception e) {
            logger.error("Something happened here: {}", e.getMessage());
        }
        return false;
    }
}
