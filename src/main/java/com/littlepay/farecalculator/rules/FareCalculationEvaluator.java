package com.littlepay.farecalculator.rules;

import com.littlepay.farecalculator.dto.TapOnOffDTO;
import com.littlepay.farecalculator.enums.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.*;

/**
 * FareCalculator is the Evaluator.
 * It holds a collection of rules that calculates fare and loops through them to find a set of rule_data.
 * Rules are just added to the collection manually here for illustrative purposes, but in a real application you
 * would more likely load them dynamically with an IoC container or something similar without having to change
 * FareCalculationEvaluator.
 */
@Service
public class FareCalculationEvaluator {

    static final Logger logger = LoggerFactory.getLogger(FareCalculationEvaluator.class);

    @Autowired
    CalculateFareRule calculateFareRule;

    @Autowired
    CalculateDurationRule calculateDurationRule;

    @Autowired
    CalculateStatusRule calculateStatusRule;

    public Map<Rule, Object> calculateFare(@Valid TapOnOffDTO tapOnOffDTO) {
        Map<Rule, Object> ruleOutcome = new HashMap<>();
        ruleOutcome.putAll(calculateStatusRule.runRule(tapOnOffDTO));
        ruleOutcome.putAll(calculateFareRule.runRule(tapOnOffDTO));
        ruleOutcome.putAll(calculateDurationRule.runRule(tapOnOffDTO));
        logger.info("Got rules outcome");

        return ruleOutcome;
    }
}
