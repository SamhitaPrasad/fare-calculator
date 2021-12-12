package com.littlepay.farecalculator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class YAMLConfig {

    private Map<String, Double> stops = new HashMap<String, Double>();

    public Map<String, Double> getStops() {
        return stops;
    }

    public void setStops(Map<String, Double> stops) {
        this.stops = stops;
    }
}
