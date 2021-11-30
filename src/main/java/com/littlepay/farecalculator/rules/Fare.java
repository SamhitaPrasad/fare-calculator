package com.littlepay.farecalculator.rules;

import com.littlepay.farecalculator.dto.TapOnOffDTO;
import com.littlepay.farecalculator.enums.Rule;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Repository
public interface Fare {

//    public void shouldRun(TapOnOffDTO tapOnOffDTO);
    public Map<Rule, Object> runRule(TapOnOffDTO tapOnOffDTO);

}
