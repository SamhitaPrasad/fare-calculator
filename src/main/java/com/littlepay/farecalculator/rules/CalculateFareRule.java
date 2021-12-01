package com.littlepay.farecalculator.rules;

import com.littlepay.farecalculator.common.Util;
import com.littlepay.farecalculator.config.YAMLConfig;
import com.littlepay.farecalculator.dto.TapOnOffDTO;
import com.littlepay.farecalculator.enums.Rule;
import com.littlepay.farecalculator.enums.TripStatus;
import com.littlepay.farecalculator.matrix.CostEstimation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * This class returns the fare based on the trip status
 */
@Component
public class CalculateFareRule implements Fare {

    @Autowired
    YAMLConfig yamlConfig;

    Logger logger = LoggerFactory.getLogger(CalculateFareRule.class);

    @Override
    public Map<Rule, Object> runRule(TapOnOffDTO tapOnOffDTO) {
        logger.info("Rules engine - Calculating fare");

        Map<Rule, Object> fareSpec = new HashMap<>();

        String matrix = yamlConfig.getMatrix();
        logger.info("Rules engine - Obtained matrix from properties {} ", matrix);

        String[][] cost = Util.convertToStringArray(matrix);

        Map<String, String> stopsMap = yamlConfig.getStops();

        if(tapOnOffDTO.getTripStatus().equals(TripStatus.INCOMPLETE)){
            //logic to calculate last stop from stop travelled using matrix
            fareSpec.put(Rule.FARE,CostEstimation.maxCostEstimateFromSource(cost,stopsMap.get(tapOnOffDTO.getTapOn().stopId)));
        }
        else{
            fareSpec.put(Rule.FARE, CostEstimation.minCostEstimate(cost, stopsMap.get(tapOnOffDTO.getTapOn().stopId), stopsMap.get(tapOnOffDTO.getTapOff().stopId)));
        }
        return fareSpec;
    }
}