package com.littlepay.farecalculator.common;

import com.littlepay.farecalculator.dto.TapOnOffDTO;

import java.time.temporal.ChronoUnit;

public class Util {

    public static long getTimeDifferenceInMillis(TapOnOffDTO tapOnOffDTO) {
        if (!equalsWithNulls(tapOnOffDTO.getTapOn(), tapOnOffDTO.getTapOff())) {
            return 0;
        } else {

            return tapOnOffDTO.getTapOn().getDateTimeUTC().until(tapOnOffDTO.getTapOff().getDateTimeUTC(), ChronoUnit.MILLIS);
        }
    }

    public static final boolean equalsWithNulls(Object a, Object b) {
        if ((a == null) || (b == null)) return false;
        else
            return true;
    }

}

