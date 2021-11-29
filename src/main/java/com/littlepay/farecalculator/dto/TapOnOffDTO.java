package com.littlepay.farecalculator.dto;

import com.littlepay.farecalculator.TripStatus;
import com.littlepay.farecalculator.common.Util;

import java.util.Objects;

public class TapOnOffDTO {

    private Taps tapOn;
    private Taps tapOff;

    private double fare;

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

    public TripStatus getTripStatus() {
        if (null != tapOn && null == tapOff)
            return TripStatus.INCOMPLETE;
        else if (Util.equalsWithNulls(tapOn, tapOff) && tapOn.tapType.equals("ON") && tapOff.tapType.equals("OFF")) {
                if (null != tapOn.stopId && null != tapOff.stopId && tapOn.stopId != tapOff.stopId)
                    return TripStatus.COMPLETE;
                else if (tapOn.stopId == tapOff.stopId)
                    return TripStatus.CANCELLED;
            }
        return null;
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
