package com.littlepay.farecalculator.rules;

import com.littlepay.farecalculator.dto.TapOnOffDTO;
import com.littlepay.farecalculator.dto.Taps;
import com.littlepay.farecalculator.enums.Rule;
import com.littlepay.farecalculator.enums.TripStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The CalculateStatusRule returns the status of the trip.
 */
@Component
public class CalculateStatusRule implements Fare {
     static final Logger logger = LoggerFactory.getLogger(CalculateStatusRule.class);

    @Override
    public Map<Rule, Object> runRule(TapOnOffDTO tapOnOffDTO) {
        logger.info("Rules engine - Calculating status");
        Map<Rule, Object> fareSpec = new HashMap<>();
        fareSpec.put(Rule.STATUS,getTripStatus(tapOnOffDTO.getTapOn(),tapOnOffDTO.getTapOff()));
        tapOnOffDTO.setTripStatus(getTripStatus(tapOnOffDTO.getTapOn(),tapOnOffDTO.getTapOff()));
        return fareSpec;
    }


    public TripStatus getTripStatus(Taps tapOn, Taps tapOff) {
        if (null != tapOn && null == tapOff)
            return TripStatus.INCOMPLETE;
        else if (!Objects.isNull(tapOn) && !Objects.isNull(tapOff) && tapOn.tapType.equals("ON") && tapOff.tapType.equals("OFF")) {
            if (null != tapOn.stopId && null != tapOff.stopId && !tapOn.stopId.equals(tapOff.stopId))
                return TripStatus.COMPLETE;
            else if (tapOn.stopId.equals(tapOff.stopId))
                return TripStatus.CANCELLED;
        }
        return null;
    }
}
