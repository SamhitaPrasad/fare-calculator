package com.littlepay.farecalculator.rules;

import com.littlepay.farecalculator.dto.TapOnOffDTO;
import com.littlepay.farecalculator.enums.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class return the duration between two stops.
 */
@Component
public class CalculateDurationRule implements Fare {
    static final Logger LOGGER = LoggerFactory.getLogger(CalculateDurationRule.class);
    private Map<Rule, Object> fareSpec = new HashMap<>();

    @Override
    public Map<Rule, Object> runRule(TapOnOffDTO tapOnOffDTO) {
        LOGGER.info("Rules engine - Calculating duration");
        fareSpec.put(Rule.DURATION, getTimeDifferenceInMillis(tapOnOffDTO));
        return fareSpec;
    }

    public long getTimeDifferenceInMillis(TapOnOffDTO tapOnOffDTO) {
        if (Objects.isNull(tapOnOffDTO.getTapOn()) || Objects.isNull(tapOnOffDTO.getTapOff())) {
            return 0;
        } else {
            return tapOnOffDTO.getTapOn().getDateTimeUTC().until(tapOnOffDTO.getTapOff().getDateTimeUTC(), ChronoUnit.MILLIS);
        }
    }
}
