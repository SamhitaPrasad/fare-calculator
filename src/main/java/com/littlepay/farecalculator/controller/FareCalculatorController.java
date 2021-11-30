package com.littlepay.farecalculator.controller;

import com.littlepay.farecalculator.dto.CSVOutput;
import com.littlepay.farecalculator.dto.TapOnOffDTO;
import com.littlepay.farecalculator.dto.Taps;
import com.littlepay.farecalculator.message.ResponseMessage;
import com.littlepay.farecalculator.parser.CSVParser;
import com.littlepay.farecalculator.service.AggregatorService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api/csv")
public class FareCalculatorController {
    Logger logger = LoggerFactory.getLogger(FareCalculatorController.class);

    @Autowired
    AggregatorService aggregatorService;

    @PostMapping("/upload")
    public ResponseEntity<List<CSVOutput>> uploadFile(@RequestParam("file") MultipartFile file) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        logger.info("Input csv file received: {}", file.getName());
        List<TapOnOffDTO> tapOnOffDTO = aggregatorService.matchAndPrice(file);
        logger.info("Processed csv for tapOn and tapOff matches");
        List<CSVOutput> csvOutputList = aggregatorService.createOutput(tapOnOffDTO);
        return ResponseEntity.status(HttpStatus.OK).body(csvOutputList);
    }
}
