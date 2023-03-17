package com.softweavers.eternity.Domain.Functions;

import java.math.BigDecimal;
import java.util.Arrays;

import static com.softweavers.eternity.Domain.FunctionParser.mc;

public class StandardDeviation {

    public static String standardDeviation(String[] input){
        BigDecimal[] values = Arrays.stream(input)
                .map(BigDecimal::new)
                .toArray(BigDecimal[]::new);

        BigDecimal mean = calculateMean(values);

        //TODO:: Change these values to BigDecimal once all functions have been implemented
        double standardDev = 0;
        for (BigDecimal value : values){
            double mu = value.subtract(mean).doubleValue();
            standardDev += Math.pow(mu, 2);
        }

        standardDev /= values.length;
        standardDev = Math.pow(standardDev, .5);
        return Double.toString(standardDev);
    }
    private static BigDecimal calculateMean(BigDecimal[] values){
        BigDecimal sum = new BigDecimal(0);
        for (BigDecimal value : values){
            sum = sum.add(value);
        }

        return sum.divide(BigDecimal.valueOf(values.length), mc);
    }
}