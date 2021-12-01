package com.littlepay.farecalculator.rules;

import com.littlepay.farecalculator.dto.TapOnOffDTO;
import com.littlepay.farecalculator.enums.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * FareCalculator is the Evaluator.
 * It holds a collection of rules that calculates fare and loops through them to find a set of rule_data.
 * Rules are just added to the collection manually here for illustrative purposes, but in a real application you
 * would more likely load them dynamically with an IoC container or something similar without having to change
 * FareCalculationEvaluator.
 *
 */
@Service
public class FareCalculationEvaluator {

    Logger logger = LoggerFactory.getLogger(FareCalculationEvaluator.class);

    @Autowired
    CalculateFareRule calculateFareRule;

    @Autowired
    CalculateDurationRule calculateDurationRule;

    @Autowired
    CalculateStatusRule calculateStatusRule;

    private final List<Fare> rules;

    public FareCalculationEvaluator() {
        //TODO: Create a new rule annotation to avoid hard coding, this will prvent hard coding of rules in lines 57-59
        rules = (Arrays.asList(calculateFareRule));
    }

    public Map<Rule, Object> calculateFare(TapOnOffDTO tapOnOffDTO) {

        Map<Rule, Object> ruleOutcome = new HashMap<>();
        //TODO: Check for errors before validating and proceeding
        if (tapOnOffDTO == null) {
            logger.error("error trying to check rules but tapOnOffDTO is empty");
            ruleOutcome.put(Rule.ERROR, "Something happened in calcuteFare");
            return ruleOutcome;
        }

        ruleOutcome.putAll(calculateStatusRule.runRule(tapOnOffDTO));
        ruleOutcome.putAll(calculateFareRule.runRule(tapOnOffDTO));
        ruleOutcome.putAll(calculateDurationRule.runRule(tapOnOffDTO));

        logger.info("Got rules outcome");
        /**
         * Uncomment this for rule validation
         */
        /*
        for ( FareRule rule : rules){
            if(rule.shouldRun(tapOnOffDTO)){
                ruleOutcome.put(rule.getClass().toString(), rule.runRule(tapOnOffDTO));
            }
        }
        */

        return ruleOutcome;
    }
}
