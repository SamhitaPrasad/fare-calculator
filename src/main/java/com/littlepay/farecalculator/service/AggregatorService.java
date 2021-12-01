package com.littlepay.farecalculator.service;

import com.littlepay.farecalculator.common.Util;
import com.littlepay.farecalculator.dto.CSVOutput;
import com.littlepay.farecalculator.dto.TapOnOffDTO;
import com.littlepay.farecalculator.dto.Taps;
import com.littlepay.farecalculator.enums.Rule;
import com.littlepay.farecalculator.enums.TripStatus;
import com.littlepay.farecalculator.exception.CSVCreationException;
import com.littlepay.farecalculator.exception.EmptyCSVException;
import com.littlepay.farecalculator.exception.RuleException;
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

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class AggregatorService {

    Logger logger = LoggerFactory.getLogger(AggregatorService.class);
    @Autowired
    private FareCalculationEvaluator fareCalculationEvaluator;

    @Autowired
    private CSVParser csvParser;

    @Autowired
    private CSVWriter csvWriter;


    public List<TapOnOffDTO> matchAndPrice(MultipartFile file) throws EmptyCSVException {
        Map<Rule, Object> ruleSpecs;
        List<Taps> allTapsList = readAndParse(file);
        if(allTapsList.size()==0){
            throw new EmptyCSVException("CSV file may contain no data");
        }
        List<TapOnOffDTO> matchedTaps = matchTrips(allTapsList);
//        FareCalculationEvaluator fareCalculationEvaluator = new FareCalculationEvaluator();
        //streams is good to have if we can have void methods, since this returns a complex data type its gets tricky to manage using streams
        //or will have to create a custom collector class, so keeping it simple.
        //Map<String, Object> ruleSpecs = matchedTaps.stream().map(tapOnOffDTO -> fareCalculationEvaluator.calculateFare(tapOnOffDTO)).collect(Map);

        for (ListIterator<TapOnOffDTO> iterator = matchedTaps.listIterator(); iterator.hasNext(); ) {
            TapOnOffDTO element = iterator.next();
            ruleSpecs = fareCalculationEvaluator.calculateFare(element);
            element.setRuleSpecs(ruleSpecs);
            //Setting null here enables us to re-use this variable for every iteration
            ruleSpecs = null;
        }
        return matchedTaps;
    }

    public List<Taps> readAndParse(MultipartFile file) throws EmptyCSVException {
        logger.info("Inside match and price");
        Reader reader = null;
        try {
            reader = new InputStreamReader(file.getInputStream());
        } catch (IOException e) {
            logger.error("Error while parsing csv file: {}", e.getMessage());
        }
        //TODO: Convert to IoC
        List<Taps> taps = csvParser.parse(reader);
        logger.info("Contents of the parse are: {}", taps.toString());
        return taps;
    }
    //TODO: Convert to concurrent list

    /**
     * matchTrips takkes in the list of allTaps received from parsing and matches TapOn and TapOff data by copying the matched taps into a tempList and removing them from the result list once matched
     * in order to reduce number of looping iterations.
     * Within the tempList we match the pan numbers to tapOn and TapOff to get objects of trips per pan number.
     * @param allTaps
     * @return List<TapOnOffDTO>
     */
    public List<TapOnOffDTO> matchTrips(List<Taps> allTaps) {
        List<TapOnOffDTO> finalList = new ArrayList<>();
        TapOnOffDTO tapOnOffDTO = null;
        List<Taps> tempList = new CopyOnWriteArrayList<>(allTaps);//new ArrayList<>(results) ;

        ListIterator<Taps> resultIterator = tempList.listIterator();
        while (resultIterator.hasNext()) {
            tapOnOffDTO = new TapOnOffDTO();
            Taps data = resultIterator.next();
            if (null != data && data.tapType.equals("ON")) {
                tapOnOffDTO.setTapOn(data);
            } else {
                tapOnOffDTO.setTapOff(data);
            }
            tempList.remove(data);
            allTaps.remove(data);
            ListIterator<Taps> tempIterator = tempList.listIterator();
            while (tempIterator.hasNext()) {
                Taps tempData = tempIterator.next();
                if (tempData.getPan().equals(data.getPan())) {
                    if (null != tempData && tempData.tapType.equals("ON")) {
                        tapOnOffDTO.setTapOn(tempData);
                    } else {
                        tapOnOffDTO.setTapOff(tempData);
                    }
                    tempList.remove(tempData);
                    allTaps.remove(tempData);
                    resultIterator = tempList.listIterator();
                    break;
                }
            }
            finalList.add(tapOnOffDTO);
            logger.info("Finished sorting data based on PAN, final tapOnOffDTO {}", finalList.toString());

        }
        return finalList;
    }

    public List<CSVOutput> createOutput(List<TapOnOffDTO> tapOnOffDTO) {
        List<CSVOutput> csvOutput = createCSVConvertableOutput(tapOnOffDTO);
        return csvOutput;
    }

    public void writeOutputCSV(List<CSVOutput> csvOutput) throws CSVCreationException {
        try {
            csvWriter.output(csvOutput);
        } catch (CsvRequiredFieldEmptyException e) {
            logger.error("CsvRequiredFieldEmptyException while reading file: {}",e.getMessage());
            throw new CSVCreationException("CSV creation exception");
        } catch (CsvDataTypeMismatchException e) {
            logger.error("CsvDataTypeMismatchException while reading file: {}",e.getMessage());
            throw new CSVCreationException("CSV creation exception");
        } catch (URISyntaxException e) {
            logger.error("URISyntaxException while reading file: {}",e.getMessage());
            throw new CSVCreationException("Exception with URI Syntax");
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
                logger.error("Invalid Status: {}", status);

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
