package com.softweavers.eternity.Domain;

import java.math.BigDecimal;

public interface FunctionHandler {
    /**
     * Computes the function arccos(x) using Taylor's Series for up to n = 16
     *
     * @param x BigDecimal value between -1 and 1
     * @return cosine inerse of x
     */
    BigDecimal arccos(BigDecimal x);

    /**
     * Computes the function logarithm(value, base) using natural log expansion and general logarithmic identities.
     *
     * @param input BigDecimal array of size two [value,base]
     * @return logarithm value of the value with respect to base provided
     */
    BigDecimal log(BigDecimal[] input);
    
    /**
     * computes the function gamma(z) using lanczos approximation
     * 
     * @param z input to the gamma function from zero to infinity
     * @return gamma of z
     */
    BigDecimal gamma(BigDecimal z);
    
    BigDecimal sinh(BigDecimal x);

    /**
     * computes the function x^y using binary approximation.
     * @param input array of BigDecimal containing:
     *        input[0] = x any real number
     *        input[1] = y any real number for x > 0 and for x being an integer value.
     *                   y any integer value for x <0 and is not an integer.
     * @return the result of x^y
     */
    BigDecimal xToY(BigDecimal[] input);

    /**
     * Returns the result of a(b^x)
     * @param values array consisting of the following indexes:
     *               values[0] = 'a' the multiplier,
     *               values[1] = 'b' the base,
     *               values[2] = 'x' the exponential coefficient
     */
    BigDecimal abx(BigDecimal[] values);

    /**
     * First calculates the mean of the given inputs. (Sums each number and divides by total operations)
     * For each value, subtracts the mean and squares that number.
     * Then divides by the total number of inputs and finishes by
     * taking the square root by raising that number to the power of a half.
     *
     * @param input array of BigDecimal numbers that will be used to calculate the standard deviation
     * @return the standard deviation of the given inputs
     */
    BigDecimal standardDeviation(BigDecimal[] input);
}
