package com.littlepay.farecalculator.service;

import com.littlepay.farecalculator.dto.CSVOutput;
import com.littlepay.farecalculator.dto.TapOnOffDTO;
import com.littlepay.farecalculator.dto.Taps;
import com.littlepay.farecalculator.enums.Rule;
import com.littlepay.farecalculator.enums.TripStatus;
import com.littlepay.farecalculator.exception.CSVCreationException;
import com.littlepay.farecalculator.exception.EmptyCSVException;
import com.littlepay.farecalculator.parser.CSVParser;
import com.littlepay.farecalculator.rules.FareCalculationEvaluator;
import com.littlepay.farecalculator.writer.CSVWriter;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AggregatorService {

    static final Logger LOGGER = LoggerFactory.getLogger(AggregatorService.class);

    @Autowired
    private FareCalculationEvaluator fareCalculationEvaluator;

    @Autowired
    private CSVParser csvParser;

    @Autowired
    private CSVWriter csvWriter;


    public List<TapOnOffDTO> matchAndPrice(MultipartFile file) throws EmptyCSVException {
        List<Taps> allTapsList = readAndParse(file);
        if (allTapsList.size() == 0) {
            throw new EmptyCSVException("CSV file may contain no data");
        }
        Map<String, Map<String, List<Taps>>> matchedTaps = matchTrips(allTapsList);
        List<TapOnOffDTO> finalMatchedList = new ArrayList<>();
        matchedTaps.entrySet().stream().forEach(element -> {
            final TapOnOffDTO tapOnOffDTO = mapToDTO(element.getValue());
            final Map<Rule, Object> ruleSpecs = fareCalculationEvaluator.calculateFare(tapOnOffDTO);
            tapOnOffDTO.setRuleSpecs(ruleSpecs);
            finalMatchedList.add(tapOnOffDTO);
        });
        return finalMatchedList;
    }

    public TapOnOffDTO mapToDTO(Map<String, List<Taps>> tapOnOffMap) {
        TapOnOffDTO tapOnOffDTO = new TapOnOffDTO();
        if (tapOnOffMap.get("ON") != null)
            tapOnOffDTO.setTapOn(tapOnOffMap.get("ON").get(0));
        if (tapOnOffMap.get("OFF") != null)
            tapOnOffDTO.setTapOff(tapOnOffMap.get("OFF").get(0));
        return tapOnOffDTO;
    }

    public List<Taps> readAndParse(MultipartFile file) throws ConstraintViolationException {
        LOGGER.info("Inside match and price");
        Reader reader = null;
        try {
            reader = new InputStreamReader(file.getInputStream());
        } catch (IOException e) {
            LOGGER.error("Error while parsing csv file: {}", e.getMessage());
        }
        List<Taps> taps = csvParser.parse(reader);
        LOGGER.info("Contents of the parse are: {}", taps.toString());
        return taps;
    }

    /**
     * matchTrips takes in the list of allTaps received from parsing and matches TapOn and TapOff data.
     * We use streams to match by pan and in the same iteration we group by tapType.
     *
     * @param allTaps
     * @return Map<String, Map<String, List<Taps>>>
     */
    public Map<String, Map<String, List<Taps>>> matchTrips(List<Taps> allTaps) throws EmptyCSVException {
        if (allTaps.size() > 0)
            return allTaps.stream()
                    .filter(e -> e != null && e.getPan() != null && !e.getPan().isEmpty() && e.getTapType() != null && !e.getTapType().isEmpty())
                    .collect(Collectors.groupingBy(Taps::getPan, Collectors.groupingBy(Taps::getTapType)));
        else throw new EmptyCSVException("Null or Empty values");

    }

    public List<CSVOutput> createOutput(List<TapOnOffDTO> tapOnOffDTO) {
        List<CSVOutput> csvOutput = createCSVConvertableOutput(tapOnOffDTO);
        return csvOutput;
    }

    public void writeOutputCSV(List<CSVOutput> csvOutput) throws CSVCreationException {
        try {
            csvWriter.output(csvOutput);
        } catch (CsvRequiredFieldEmptyException e) {
            LOGGER.error("CsvRequiredFieldEmptyException while reading file: {}", e.getMessage());
            throw new CSVCreationException("CSV creation exception",csvOutput);
        } catch (CsvDataTypeMismatchException e) {
            LOGGER.error("CsvDataTypeMismatchException while reading file: {}", e.getMessage());
            throw new CSVCreationException("CSV creation exception",csvOutput);
        } catch (URISyntaxException e) {
            LOGGER.error("URISyntaxException while reading file: {}", e.getMessage());
            throw new CSVCreationException("Exception with URI Syntax",csvOutput);
        }
    }

    private List<CSVOutput> createCSVConvertableOutput(List<TapOnOffDTO> tapOnOffDTO) {
        List<CSVOutput> csvOutputList = new ArrayList<>();
        for (ListIterator<TapOnOffDTO> iterator = tapOnOffDTO.listIterator(); iterator.hasNext(); ) {
            TapOnOffDTO tapOnOffDTOElement = iterator.next();
            CSVOutput csvOutput = mapToBean(tapOnOffDTOElement);
            csvOutputList.add(csvOutput);
        }
        return csvOutputList;
    }

    private CSVOutput mapToBean(TapOnOffDTO tapOnOffDTOElement) {
        CSVOutput csvOutput = new CSVOutput();

        TripStatus status = (TripStatus) tapOnOffDTOElement.getRuleSpecs().get(Rule.STATUS);
        switch (status) {
            case INCOMPLETE:
                beanForIncomplete(tapOnOffDTOElement, csvOutput, status);
                break;
            case COMPLETE:
            case CANCELLED:
                beanForCompletedAndCancelled(tapOnOffDTOElement, csvOutput, status);
                break;
            default:
                LOGGER.error("Invalid Status: {}", status);

        }

        return csvOutput;
    }

    private void beanForIncomplete(TapOnOffDTO tapOnOffDTOElement, CSVOutput csvOutput, TripStatus status) {
        csvOutput.setDurationSecs((long) tapOnOffDTOElement.getRuleSpecs().get(Rule.DURATION));
        csvOutput.setStarted(tapOnOffDTOElement.getTapOn().getDateTimeUTC());
        csvOutput.setFromStopId(tapOnOffDTOElement.getTapOn().getStopId());
        csvOutput.setBusID(tapOnOffDTOElement.getTapOn().getBusId());
        csvOutput.setPan(tapOnOffDTOElement.getTapOn().getPan());
        csvOutput.setCompanyId(tapOnOffDTOElement.getTapOn().getCompanyId());
        csvOutput.setChargeAmount(Double.parseDouble(tapOnOffDTOElement.getRuleSpecs().get(Rule.FARE).toString()));
        csvOutput.setStatus(status);
    }

    private void beanForCompletedAndCancelled(TapOnOffDTO tapOnOffDTOElement, CSVOutput csvOutput, TripStatus status) {
        csvOutput.setDurationSecs((long) tapOnOffDTOElement.getRuleSpecs().get(Rule.DURATION));
        csvOutput.setStarted(tapOnOffDTOElement.getTapOn().getDateTimeUTC());
        csvOutput.setFinished(tapOnOffDTOElement.getTapOff().getDateTimeUTC());
        csvOutput.setFromStopId(tapOnOffDTOElement.getTapOn().getStopId());
        csvOutput.setToStopId(tapOnOffDTOElement.getTapOff().getStopId());
        csvOutput.setBusID(tapOnOffDTOElement.getTapOff().getBusId());
        csvOutput.setPan(tapOnOffDTOElement.getTapOff().getPan());
        csvOutput.setCompanyId(tapOnOffDTOElement.getTapOff().getCompanyId());
        csvOutput.setChargeAmount(Double.parseDouble(tapOnOffDTOElement.getRuleSpecs().get(Rule.FARE).toString()));
        csvOutput.setStatus(status);
    }
}
