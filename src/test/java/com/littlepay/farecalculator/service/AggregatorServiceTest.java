package com.littlepay.farecalculator.service;

import com.littlepay.farecalculator.dto.TapOnOffDTO;
import com.littlepay.farecalculator.dto.Taps;
import com.littlepay.farecalculator.exception.EmptyCSVException;
import com.littlepay.farecalculator.exception.RuleException;
import com.littlepay.farecalculator.parser.CSVParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.*;

public class AggregatorServiceTest {
    @Test
    public void matchAndPrice_success() throws IOException, RuleException, EmptyCSVException {
        //Given - if you send a multipart file, it returns a list of TapOnOFFDto
        MultipartFile file = Mockito.mock(MultipartFile.class);
        AggregatorService aggregatorService = Mockito.mock(AggregatorService.class);
        Reader reader = Mockito.mock(Reader.class);
        CSVParser csvParser = Mockito.mock(CSVParser.class);
        Taps tapOn = new Taps("", null,"ON","Stop1","","","123");
        Taps tapOff = new Taps("", null,"OFF","Stop2","","","123");
        List<Taps> allTapsList = new ArrayList<>();
        allTapsList.add(tapOn);
        allTapsList.add(tapOff);

        TapOnOffDTO tapOnOffDTO = new TapOnOffDTO();
        tapOnOffDTO.setTapOn(tapOn);
        tapOnOffDTO.setTapOff(tapOff);
        List<TapOnOffDTO> tapOnOffDTOList = new ArrayList<>();
        tapOnOffDTOList.add(tapOnOffDTO);


        Map<String, Map<String, List<Taps>>> expectedTapOnOffDTOS = new HashMap<>();
        Map<String, List<Taps>> expectedTaps = new HashMap<>();
        List<Taps> listTapOn = new ArrayList<>();
        List<Taps> listTapOff = new ArrayList<>();
        listTapOn.add(tapOn);
        listTapOff.add(tapOff);
        expectedTaps.put("ON",listTapOn);
        expectedTaps.put("OFF",listTapOff);
        expectedTapOnOffDTOS.put("123",expectedTaps);
        Mockito.when(csvParser.parse(reader)).thenReturn(allTapsList);
        Mockito.when(aggregatorService.readAndParse(file)).thenReturn(allTapsList);
        Mockito.when(aggregatorService.matchTrips(allTapsList)).thenReturn(expectedTapOnOffDTOS);
        Mockito.when(aggregatorService.matchAndPrice(file)).thenReturn(tapOnOffDTOList);
        Assertions.assertEquals(tapOnOffDTOList,aggregatorService.matchAndPrice(file));

    }


    @Test
    public void matchTrips_empty_list() {
        AggregatorService aggregatorService = new AggregatorService();
        List<Taps> allTaps = new ArrayList<>();
        Assertions.assertThrows(EmptyCSVException.class, () ->  aggregatorService.matchTrips(allTaps));
    }

    @Test
    public void matchTrips_null_values_list() throws EmptyCSVException {
        AggregatorService aggregatorService = new AggregatorService();
        List<Taps> allTaps = new ArrayList<>();
        allTaps.add(null);
        Assertions.assertEquals(Collections.EMPTY_MAP, aggregatorService.matchTrips(allTaps));
    }

    @Test
    public void matchTrips_null_values() throws EmptyCSVException {
        AggregatorService aggregatorService = new AggregatorService();
        List<Taps> allTaps = new ArrayList<>();
        Taps taps = new Taps("", null,"","","","","");
        allTaps.add(taps);
        Assertions.assertEquals(Collections.EMPTY_MAP, aggregatorService.matchTrips(allTaps));
    }

    @Test
    public void matchTrips_complete() throws EmptyCSVException{
        //Given
        AggregatorService aggregatorService = new AggregatorService();
        List<Taps> allTaps = new ArrayList<>();
        Taps tapOn = new Taps("", null,"ON","","","","123");
        Taps tapOff = new Taps("", null,"OFF","","","","123");
        allTaps.add(tapOn);
        allTaps.add(tapOff);
        //When
        TapOnOffDTO tapOnOffDTO = new TapOnOffDTO();
        tapOnOffDTO.setTapOn(tapOn);
        tapOnOffDTO.setTapOff(tapOff);
        //Then
        Map<String, Map<String, List<Taps>>> expectedTapOnOffDTOS = new HashMap<>();
        Map<String, List<Taps>> expectedTaps = new HashMap<>();
        List<Taps> listTapOn = new ArrayList<>();
        List<Taps> listTapOff = new ArrayList<>();
        listTapOn.add(tapOn);
        listTapOff.add(tapOff);
        expectedTaps.put("ON",listTapOn);
        expectedTaps.put("OFF",listTapOff);
        expectedTapOnOffDTOS.put("123",expectedTaps);
        Assertions.assertEquals(expectedTapOnOffDTOS, aggregatorService.matchTrips(allTaps));
    }

    @Test
    public void matchTrips_incomplete() throws EmptyCSVException{
        AggregatorService aggregatorService = new AggregatorService();
        List<Taps> allTaps = new ArrayList<>();
        Taps tapOn = new Taps("", null,"ON","","","","123");
        allTaps.add(tapOn);
        TapOnOffDTO tapOnOffDTO = new TapOnOffDTO();
        tapOnOffDTO.setTapOn(tapOn);
        Map<String, Map<String, List<Taps>>> expectedTapOnOffDTOS = new HashMap<>();
        Map<String, List<Taps>> expectedTaps = new HashMap<>();
        List<Taps> listTapOn = new ArrayList<>();
        listTapOn.add(tapOn);
        expectedTaps.put("ON",listTapOn);
        expectedTapOnOffDTOS.put("123",expectedTaps);
        Assertions.assertEquals(expectedTapOnOffDTOS, aggregatorService.matchTrips(allTaps));
    }

    @Test
    public void matchTrips_cancelled() throws EmptyCSVException{
        AggregatorService aggregatorService = new AggregatorService();
        List<Taps> allTaps = new ArrayList<>();
        Taps tapOn = new Taps("", null,"ON","Stop1","","","123");
        Taps tapOff = new Taps("", null,"OFF","Stop1","","","123");
        allTaps.add(tapOn);
        allTaps.add(tapOff);
        TapOnOffDTO tapOnOffDTO = new TapOnOffDTO();
        tapOnOffDTO.setTapOn(tapOn);
        tapOnOffDTO.setTapOff(tapOff);
        Map<String, Map<String, List<Taps>>> expectedTapOnOffDTOS = new HashMap<>();
        Map<String, List<Taps>> expectedTaps = new HashMap<>();
        List<Taps> listTapOn = new ArrayList<>();
        List<Taps> listTapOff = new ArrayList<>();
        listTapOn.add(tapOn);
        listTapOff.add(tapOff);
        expectedTaps.put("ON",listTapOn);
        expectedTaps.put("OFF",listTapOff);
        expectedTapOnOffDTOS.put("123",expectedTaps);
        Assertions.assertEquals(expectedTapOnOffDTOS, aggregatorService.matchTrips(allTaps));
    }

    @Test
    public void mapToDTO_success(){
        //Given
        AggregatorService aggregatorService = new AggregatorService();
        Taps tapOn = new Taps("", null,"ON","","","","123");
        Taps tapOff = new Taps("", null,"OFF","","","","123");
        //When
        TapOnOffDTO expectedTapOnOffDTO = new TapOnOffDTO();
        expectedTapOnOffDTO.setTapOn(tapOn);
        expectedTapOnOffDTO.setTapOff(tapOff);
        //Then
        Map<String, List<Taps>> allTaps = new HashMap<>();
        List<Taps> listTapOn = new ArrayList<>();
        List<Taps> listTapOff = new ArrayList<>();
        listTapOn.add(tapOn);
        listTapOff.add(tapOff);
        allTaps.put("ON",listTapOn);
        allTaps.put("OFF",listTapOff);
        Assertions.assertEquals(expectedTapOnOffDTO, aggregatorService.mapToDTO(allTaps));
    }

    @Test
    public void mapToDTO_success_incomplete(){
        //Given
        AggregatorService aggregatorService = new AggregatorService();
        Taps tapOn = new Taps("", null,"ON","","","","123");
        //When
        TapOnOffDTO expectedTapOnOffDTO = new TapOnOffDTO();
        expectedTapOnOffDTO.setTapOn(tapOn);
        //Then
        Map<String, List<Taps>> allTaps = new HashMap<>();
        List<Taps> listTapOn = new ArrayList<>();
        listTapOn.add(tapOn);
        allTaps.put("ON",listTapOn);
        Assertions.assertEquals(expectedTapOnOffDTO, aggregatorService.mapToDTO(allTaps));
    }
}
