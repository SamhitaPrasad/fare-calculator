package com.littlepay.farecalculator.service;

import com.littlepay.farecalculator.dto.TapOnOffDTO;
import com.littlepay.farecalculator.dto.Taps;
import com.littlepay.farecalculator.exception.EmptyCSVException;
import com.littlepay.farecalculator.parser.CSVParser;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import java.io.Reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;


public class AggregatorServiceTest {

    TapOnOffDTO tapOnOffDTO;
    Taps tapOn;
    Taps tapOff;
    List<Taps> allTapsList;
    Map<String, Map<String, List<Taps>>> expectedTapOnOffDTOS;
    Map<String, List<Taps>> expectedTaps;
    List<Taps> listTapOn;
    List<Taps> listTapOff;
    List<TapOnOffDTO> tapOnOffDTOList;
    AggregatorService aggregatorService;
    Map<String, List<Taps>> allTapsMap;

    @BeforeEach
    public void init() {
        tapOnOffDTO = new TapOnOffDTO();
        tapOn = new Taps();
        tapOff = new Taps();
        allTapsList = new ArrayList<>();
        listTapOn = new ArrayList<>();
        listTapOff = new ArrayList<>();
        expectedTapOnOffDTOS = new HashMap<>();
        expectedTaps = new HashMap<>();
        tapOnOffDTOList = new ArrayList<>();
        aggregatorService = new AggregatorService();
        allTapsMap = new HashMap<>();
    }

    @Test
    public void matchAndPrice_success() throws EmptyCSVException {
        //Given - if you send a multipart file, it returns a list of TapOnOFFDto
        MultipartFile file = Mockito.mock(MultipartFile.class);
        AggregatorService aggregatorServiceMock = Mockito.mock(AggregatorService.class);
        Reader reader = Mockito.mock(Reader.class);
        CSVParser csvParser = Mockito.mock(CSVParser.class);
        tapOn = new Taps("", null, "ON", "Stop1", "", "", "123");
        tapOff = new Taps("", null, "OFF", "Stop2", "", "", "123");
        allTapsList = new ArrayList<>();
        allTapsList.add(tapOn);
        allTapsList.add(tapOff);

        tapOnOffDTO.setTapOn(tapOn);
        tapOnOffDTO.setTapOff(tapOff);
        tapOnOffDTOList.add(tapOnOffDTO);

        listTapOn.add(tapOn);
        listTapOff.add(tapOff);
        expectedTaps.put("ON", listTapOn);
        expectedTaps.put("OFF", listTapOff);
        expectedTapOnOffDTOS.put("123", expectedTaps);
        Mockito.when(csvParser.parse(reader)).thenReturn(allTapsList);
        Mockito.when(aggregatorServiceMock.readAndParse(file)).thenReturn(allTapsList);
        Mockito.when(aggregatorServiceMock.matchTrips(allTapsList)).thenReturn(expectedTapOnOffDTOS);
        Mockito.when(aggregatorServiceMock.matchAndPrice(file)).thenReturn(tapOnOffDTOList);
        Assertions.assertEquals(tapOnOffDTOList, aggregatorServiceMock.matchAndPrice(file));
    }

    @Test
    public void matchTrips_empty_list() {
        Assertions.assertThrows(EmptyCSVException.class, () -> aggregatorService.matchTrips(new ArrayList<>()));
    }

    @Test
    public void matchTrips_null_values_list() throws EmptyCSVException {
        allTapsList.add(null);
        Assertions.assertEquals(Collections.EMPTY_MAP, aggregatorService.matchTrips(allTapsList));
    }

    @Test
    public void matchTrips_null_values() throws EmptyCSVException {
        tapOn = new Taps("", null, "", "", "", "", "");
        allTapsList.add(tapOn);
        Assertions.assertEquals(Collections.EMPTY_MAP, aggregatorService.matchTrips(allTapsList));
    }

    @Test
    public void matchTrips_complete() throws EmptyCSVException {
        //Given
        tapOn = new Taps("", null, "ON", "", "", "", "123");
        tapOff = new Taps("", null, "OFF", "", "", "", "123");
        allTapsList.add(tapOn);
        allTapsList.add(tapOff);
        //When
        tapOnOffDTO.setTapOn(tapOn);
        tapOnOffDTO.setTapOff(tapOff);
        //Then
        listTapOn.add(tapOn);
        listTapOff.add(tapOff);
        expectedTaps.put("ON", listTapOn);
        expectedTaps.put("OFF", listTapOff);
        expectedTapOnOffDTOS.put("123", expectedTaps);
        Assertions.assertEquals(expectedTapOnOffDTOS, aggregatorService.matchTrips(allTapsList));
    }

    @Test
    public void matchTrips_incomplete() throws EmptyCSVException {
        tapOn = new Taps("", null, "ON", "", "", "", "123");
        allTapsList.add(tapOn);
        tapOnOffDTO.setTapOn(tapOn);
        listTapOn.add(tapOn);
        expectedTaps.put("ON", listTapOn);
        expectedTapOnOffDTOS.put("123", expectedTaps);
        Assertions.assertEquals(expectedTapOnOffDTOS, aggregatorService.matchTrips(allTapsList));
    }

    @Test
    public void matchTrips_cancelled() throws EmptyCSVException {
        tapOn = new Taps("", null, "ON", "Stop1", "", "", "123");
        tapOff = new Taps("", null, "OFF", "Stop1", "", "", "123");
        allTapsList.add(tapOn);
        allTapsList.add(tapOff);
        tapOnOffDTO.setTapOn(tapOn);
        tapOnOffDTO.setTapOff(tapOff);

        listTapOn.add(tapOn);
        listTapOff.add(tapOff);
        expectedTaps.put("ON", listTapOn);
        expectedTaps.put("OFF", listTapOff);
        expectedTapOnOffDTOS.put("123", expectedTaps);
        Assertions.assertEquals(expectedTapOnOffDTOS, aggregatorService.matchTrips(allTapsList));
    }

    @Test
    public void mapToDTO_success() {
        //Given
        tapOn = new Taps("", null, "ON", "", "", "", "123");
        tapOff = new Taps("", null, "OFF", "", "", "", "123");
        //When
        tapOnOffDTO.setTapOn(tapOn);
        tapOnOffDTO.setTapOff(tapOff);
        //Then
        listTapOn.add(tapOn);
        listTapOff.add(tapOff);
        allTapsMap.put("ON", listTapOn);
        allTapsMap.put("OFF", listTapOff);
        Assertions.assertEquals(tapOnOffDTO, aggregatorService.mapToDTO(allTapsMap));
    }

    @Test
    public void mapToDTO_success_incomplete() {
        //Given
        Taps tapOn = new Taps("", null, "ON", "", "", "", "123");
        //When
        tapOnOffDTO.setTapOn(tapOn);
        //Then
        listTapOn.add(tapOn);
        allTapsMap.put("ON", listTapOn);
        Assertions.assertEquals(tapOnOffDTO, aggregatorService.mapToDTO(allTapsMap));
    }

    @AfterEach
    public void destroy() {
        tapOnOffDTO = null;
        tapOn = null;
        tapOff = null;
        allTapsList = null;
        listTapOn = null;
        listTapOff = null;
        expectedTapOnOffDTOS = null;
        expectedTaps = null;
        tapOnOffDTOList = null;
        aggregatorService = null;
    }
}
