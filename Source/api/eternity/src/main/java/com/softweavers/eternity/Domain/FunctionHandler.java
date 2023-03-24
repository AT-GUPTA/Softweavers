package com.softweavers.eternity.Domain;

import java.math.BigDecimal;

public interface FunctionHandler {
    BigDecimal arccos(BigDecimal x);

    BigDecimal pow(BigDecimal[] input);

    BigDecimal log(BigDecimal[] input);

    BigDecimal gamma(BigDecimal z);

    BigDecimal sinh(BigDecimal x);

    BigDecimal xToY(BigDecimal y);

    /**
     * First calculates the mean of the given inputs. (Sums each number and divides by total operations)
     * For each value we subtract the mean and squares that number.
     * Then divides by the total number of inputs and finishes by
     * taking the square root by raising that number to the power of a half.
     * @param input array of BigDecimal numbers that will be used to calculate the standard deviation
     * @return the standard deviation of the given inputs
     */
    BigDecimal standardDeviation(BigDecimal[] input);
}
