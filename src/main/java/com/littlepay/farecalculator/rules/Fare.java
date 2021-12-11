package com.littlepay.farecalculator.rules;

import com.littlepay.farecalculator.dto.TapOnOffDTO;
import com.littlepay.farecalculator.enums.Rule;
import org.springframework.stereotype.Repository;

/**
 * Rules design pattern helps the developer to encapsulate each business rule in a separate object and decouple the definition of business rules from their processing.
 * New rules can be added without the need to modify the rest of the application logic
 */

import java.util.Map;

@Repository
public interface Fare {
// Should you choose to run a rule based on a particular flag, this helper method wil be handy.
    public Map<Rule, Object> runRule(TapOnOffDTO tapOnOffDTO);

}
