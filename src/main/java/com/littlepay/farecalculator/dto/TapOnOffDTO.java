package com.littlepay.farecalculator.dto;

import com.littlepay.farecalculator.enums.Rule;
import com.littlepay.farecalculator.enums.TripStatus;

import java.util.Map;
import java.util.Objects;

public class TapOnOffDTO {

    private Taps tapOn;
    private Taps tapOff;

    private double fare;
    private Map<Rule,Object> ruleSpecs;

    public Map<Rule, Object> getRuleSpecs() {
        return ruleSpecs;
    }

    public void setRuleSpecs(Map<Rule, Object> ruleSpecs) {
        this.ruleSpecs = ruleSpecs;
    }

    public TripStatus getTripStatus() {
        return tripStatus;
    }



    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    private TripStatus tripStatus;

    public TapOnOffDTO() {
    }

    public TapOnOffDTO(Taps tapOn, Taps tapOff) {
        this.tapOn = tapOn;
        this.tapOff = tapOff;
    }

    public Taps getTapOn() {
        return tapOn;
    }

    public void setTapOn(Taps tapOn) {
        this.tapOn = tapOn;
    }

    public Taps getTapOff() {
        return tapOff;
    }

    public void setTapOff(Taps tapOff) {
        this.tapOff = tapOff;
    }

    public void setTripStatus(TripStatus tripStatus) {
        this.tripStatus = tripStatus;
    }

    @Override
    public String toString() {
        return "TapOnOffDTO{" +
                "tapOn=" + tapOn +
                ", tapOff=" + tapOff +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TapOnOffDTO)) return false;
        TapOnOffDTO that = (TapOnOffDTO) o;
        return Objects.equals(getTapOn(), that.getTapOn()) && Objects.equals(getTapOff(), that.getTapOff());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTapOn(), getTapOff());
    }
}
