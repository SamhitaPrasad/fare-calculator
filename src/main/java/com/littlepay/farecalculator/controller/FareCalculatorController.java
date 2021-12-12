package com.littlepay.farecalculator.controller;

import com.littlepay.farecalculator.dto.CSVOutput;
import com.littlepay.farecalculator.dto.TapOnOffDTO;
import com.littlepay.farecalculator.exception.CSVCreationException;
import com.littlepay.farecalculator.exception.EmptyCSVException;
import com.littlepay.farecalculator.service.AggregatorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@Validated
@RequestMapping("/api/csv")
public class FareCalculatorController {
    static final Logger LOGGER = LoggerFactory.getLogger(FareCalculatorController.class);

    @Autowired
    AggregatorService aggregatorService;

    @PostMapping("/upload")
    public ResponseEntity<List<?>> uploadFile(@RequestParam("file") MultipartFile file) throws EmptyCSVException, CSVCreationException {
        LOGGER.info("Input csv file received: {}", file.getName());
        List<TapOnOffDTO> tapOnOffDTO = aggregatorService.matchAndPrice(file);

        LOGGER.info("Processed csv for tapOn and tapOff matches");

        List<@Valid CSVOutput> csvOutputList = aggregatorService.createOutput(tapOnOffDTO);
        aggregatorService.writeOutputCSV(csvOutputList);
        return ResponseEntity.status(HttpStatus.OK).body(csvOutputList);
    }
}
