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
        if ((a == null) || (b == null))
            return false;
        else
            return true;
    }

    public static final boolean equalsWithNulls(Object a) {
        if (a == null)
            return false;
        else
            return true;
    }

    public static String[][] convertToStringArray(String s) {
        s = s.replace("{", "");//replacing all [ to ""
        s = s.substring(0, s.length() - 2);//ignoring last two ]]
        String s1[] = s.split("},");//separating all by "],"

        String cost[][] = new String[s1.length][s1.length]; //declaring two-dimensional matrix for input

        for (int i = 0; i < s1.length; i++) {
            s1[i] = s1[i].trim();//ignoring all extra space if the string s1[i] has
            String singleInt[] = s1[i].split(", ");//separating integers by ", "
            for (int j = 0; j < singleInt.length; j++) {
                cost[i][j] = singleInt[j];//adding single values
            }
        }
        return cost;
    }
}

