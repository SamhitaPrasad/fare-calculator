package com.littlepay.farecalculator.rules;

import com.littlepay.farecalculator.common.Util;
import com.littlepay.farecalculator.dto.TapOnOffDTO;
import com.littlepay.farecalculator.enums.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * This class return the duration between two stops.
 */
@Component
public class CalculateDurationRule implements Fare{
    Logger logger = LoggerFactory.getLogger(CalculateDurationRule.class);
    @Override
    public Map<Rule, Object> runRule(TapOnOffDTO tapOnOffDTO) {
        logger.info("Rules engine - Calculating duration");
        Map<Rule, Object> fareSpec = new HashMap<>();
        fareSpec.put(Rule.DURATION, Util.getTimeDifferenceInMillis(tapOnOffDTO));
        return fareSpec;
    }
}
