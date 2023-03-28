package com.am.calculator;

import java.util.regex.Pattern;

public abstract class CalculationSupport {
    public static String prepareStringToCalculate(String number) {
        if (Pattern.matches("\\d+(,)\\d+", number)) {
            String[] splitNumber = number.split(",");
            return splitNumber[0] + "." + splitNumber[1];
        } else if (Pattern.matches("\\d+(,)", number)) {
            String[] splitString = number.split(",");
            return (splitString[0] + ".");
        }
        return number;
    }
}
