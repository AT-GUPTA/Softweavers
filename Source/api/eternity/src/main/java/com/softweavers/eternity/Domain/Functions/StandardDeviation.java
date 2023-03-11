package com.softweavers.eternity.Domain.Functions;

import java.util.Arrays;

public class StandardDeviation {
    public static double calculateMean(double[] values){
        double sum = 0;
        for (double value : values){
            sum += value;
        }

        return sum/values.length;
    }

    public static String standardDeviation(String[] input){
        //TODO:: big dec
        double[] values = Arrays.stream(input).mapToDouble(Double::parseDouble).toArray();

        double mean = calculateMean(values);
        double standardDev = 0;
        for (double value : values){
            standardDev += Math.pow((value - mean), 2);
        }

        standardDev /= values.length;
        standardDev = Math.pow(standardDev, .5);
        return Double.toString(standardDev);
    }
}
