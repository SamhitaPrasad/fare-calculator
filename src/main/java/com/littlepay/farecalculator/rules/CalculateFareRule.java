package com.littlepay.farecalculator.rules;

import com.littlepay.farecalculator.dto.TapOnOffDTO;
import com.littlepay.farecalculator.enums.Rule;
import com.littlepay.farecalculator.enums.TripStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * This class returns the fare based on the trip status
 */
@Component
@EnableConfigurationProperties
@ConfigurationProperties
public class CalculateFareRule implements Fare {

    static final Logger LOGGER = LoggerFactory.getLogger(CalculateFareRule.class);

    Map<String, Double> stops;

    @Autowired
    public CalculateFareRule(Map<String, Double> stops) {
        this.stops = stops;
    }

    @Override
    public Map<Rule, Object> runRule(TapOnOffDTO tapOnOffDTO) {
        LOGGER.info("Rules engine - Calculating fare");

        Map<Rule, Object> fareSpec = new HashMap<>();
        Optional<Double> fare;
        if (tapOnOffDTO.getTripStatus().equals(TripStatus.INCOMPLETE)) {
            fare = getCostForIncompleteTrip(tapOnOffDTO.getTapOn().getStopId());
        } else {
            fare = getCostForCompleteCancelledTrip(tapOnOffDTO.getTapOn().getStopId(), tapOnOffDTO.getTapOff().getStopId());
        }
        fareSpec.put(Rule.FARE, fare.get());
        return fareSpec;
    }

    public Optional<Double> getCostForCompleteCancelledTrip(String fromStopKey, String toStopKey) {
        return Optional.of(getStops().get(fromStopKey+toStopKey));
    }

    public Optional<Double> getCostForIncompleteTrip(String fromStopKey) {
        return getStops().entrySet().stream()
                .filter(entrySet -> entrySet.getKey().matches("^" + fromStopKey + ".*$"))
                .map(stringDoubleEntry -> stringDoubleEntry.getValue())
                .max(Comparator.naturalOrder());
    }

    public Map<String, Double> getStops() {
        return stops;
    }

    public void setStops(Map<String, Double> stops) {
        this.stops = stops;
    }
}

