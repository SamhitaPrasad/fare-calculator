package com.littlepay.farecalculator.service;

import com.littlepay.farecalculator.dto.TapOnOffDTO;
import com.littlepay.farecalculator.dto.Taps;
import com.littlepay.farecalculator.exception.EmptyCSVException;
import com.littlepay.farecalculator.exception.RuleException;
import com.littlepay.farecalculator.parser.CSVParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AggregatorServiceTest {
    @Test
    public void matchAndPrice_success() throws IOException, RuleException, EmptyCSVException {
        //Given - if you send a multipart file, it returns a list of TapOnOFFDto
        MultipartFile file = Mockito.mock(MultipartFile.class);
        AggregatorService aggregatorService = Mockito.mock(AggregatorService.class);
        Reader reader = Mockito.mock(Reader.class);
        CSVParser csvParser = Mockito.mock(CSVParser.class);
        Taps tapOn = new Taps("", null,"ON","Stop1","","","123");
        Taps tapOff = new Taps("", null,"OFF","","Stop2","","123");
        List<Taps> allTapsList = new ArrayList<>();
        allTapsList.add(tapOn);
        allTapsList.add(tapOff);
        TapOnOffDTO expectedTapOnOffDTO = new TapOnOffDTO();
        expectedTapOnOffDTO.setTapOn(tapOn);
        expectedTapOnOffDTO.setTapOff(tapOff);
        List<TapOnOffDTO> expectedTapOnOffDTOS = new ArrayList<>();
        expectedTapOnOffDTOS.add(expectedTapOnOffDTO);
        Mockito.when(csvParser.parse(reader)).thenReturn(allTapsList);
        Mockito.when(aggregatorService.readAndParse(file)).thenReturn(allTapsList);
        Mockito.when(aggregatorService.matchTrips(allTapsList)).thenReturn(expectedTapOnOffDTOS);
        Mockito.when(aggregatorService.matchAndPrice(file)).thenReturn(expectedTapOnOffDTOS);
        Assertions.assertEquals(aggregatorService.matchAndPrice(file), expectedTapOnOffDTOS);

    }

    @Test
    public void readAndParse_success() throws EmptyCSVException {
        //Given - if you send a multipart file, it returns a list of Taps
        MultipartFile file = Mockito.mock(MultipartFile.class);
        Reader reader = Mockito.mock(Reader.class);
        CSVParser csvParser = Mockito.mock(CSVParser.class);
        List<Taps> expectedTaps = new ArrayList<>();
        Taps taps = new Taps();
        expectedTaps.add(taps);
        //When we call parser
        Mockito.when(csvParser.parse(reader)).thenReturn(expectedTaps);
        Assertions.assertEquals(expectedTaps.size(),1);
    }

//    @Test
//    public void readAndParse_IOE(){
//        //Given - if you send a multipart file, it returns a list of Taps
//        MultipartFile file = null;
//        Reader reader = null;
//        CSVParser csvParser = Mockito.mock(CSVParser.class);
//        List<Taps> expectedTaps = new ArrayList<>();
//        //When we call parser
//        Mockito.when(csvParser.parse(reader)).thenThrow(IOException.class);
//    }

    @Test
    public void matchTrips_null_values(){
        AggregatorService aggregatorService = new AggregatorService();
        List<Taps> allTaps = new ArrayList<>();
        Taps taps = new Taps("", null,"","","","","");
        allTaps.add(null);
        TapOnOffDTO tapOnOffDTO = new TapOnOffDTO();
        List<TapOnOffDTO> expectedTapOnOffDTOS = new ArrayList<>();
        expectedTapOnOffDTOS.add(tapOnOffDTO);
        Assertions.assertEquals(aggregatorService.matchTrips(allTaps),expectedTapOnOffDTOS);
    }

    @Test
    public void matchTrips_complete(){
        AggregatorService aggregatorService = new AggregatorService();
        List<Taps> allTaps = new ArrayList<>();
        Taps tapOn = new Taps("", null,"ON","","","","123");
        Taps tapOff = new Taps("", null,"OFF","","","","123");
        allTaps.add(tapOn);
        allTaps.add(tapOff);
        TapOnOffDTO tapOnOffDTO = new TapOnOffDTO();
        tapOnOffDTO.setTapOn(tapOn);
        tapOnOffDTO.setTapOff(tapOff);
        List<TapOnOffDTO> expectedTapOnOffDTOS = new ArrayList<>();
        expectedTapOnOffDTOS.add(tapOnOffDTO);
        Assertions.assertEquals(aggregatorService.matchTrips(allTaps),expectedTapOnOffDTOS);
    }

    @Test
    public void matchTrips_incomplete(){
        AggregatorService aggregatorService = new AggregatorService();
        List<Taps> allTaps = new ArrayList<>();
        Taps tapOn = new Taps("", null,"ON","","","","123");
        allTaps.add(tapOn);
        TapOnOffDTO tapOnOffDTO = new TapOnOffDTO();
        tapOnOffDTO.setTapOn(tapOn);
        List<TapOnOffDTO> expectedTapOnOffDTOS = new ArrayList<>();
        expectedTapOnOffDTOS.add(tapOnOffDTO);
        Assertions.assertEquals(aggregatorService.matchTrips(allTaps),expectedTapOnOffDTOS);
    }

    @Test
    public void matchTrips_cancelled(){
        AggregatorService aggregatorService = new AggregatorService();
        List<Taps> allTaps = new ArrayList<>();
        Taps tapOn = new Taps("", null,"ON","Stop1","","","123");
        Taps tapOff = new Taps("", null,"OFF","Stop1","","","123");
        allTaps.add(tapOn);
        allTaps.add(tapOff);
        TapOnOffDTO tapOnOffDTO = new TapOnOffDTO();
        tapOnOffDTO.setTapOn(tapOn);
        tapOnOffDTO.setTapOff(tapOff);
        List<TapOnOffDTO> expectedTapOnOffDTOS = new ArrayList<>();
        expectedTapOnOffDTOS.add(tapOnOffDTO);
        Assertions.assertEquals(aggregatorService.matchTrips(allTaps),expectedTapOnOffDTOS);
    }
}
