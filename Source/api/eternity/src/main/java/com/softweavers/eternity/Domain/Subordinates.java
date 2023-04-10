package com.softweavers.eternity.Domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class Subordinates {
    final static int NDIGITS = 10;

    private static final Logger LOGGER = LoggerFactory.getLogger(Subordinates.class);
    private static final MathContext PRECISION = MathContext.DECIMAL128;

    //todo cannot use pow function here. Maybe write the hard value.
    private final static BigDecimal THRESHOLD = BigDecimal.ONE.divide(BigDecimal.TEN.pow(2 * NDIGITS), PRECISION);
    private static final BigDecimal ln1000 = BigDecimal.valueOf(6.907755278982137650156515175517786);

    // Method to calculate decimal sqrt of a number
    private BigDecimal decimalSqrt(BigDecimal number, BigDecimal i, BigDecimal j) {
        BigDecimal midvalue = i.add(j).divide(BigDecimal.valueOf(2), PRECISION);
        BigDecimal square = midvalue.multiply(midvalue);
        BigDecimal error = square.subtract(number).abs();
        if (square.equals(number) || error.compareTo(THRESHOLD) < 0)
            return midvalue;
        else if (square.compareTo(number) > 0)
            return decimalSqrt(number, i, midvalue);
        else
            return decimalSqrt(number, midvalue, j);
    }

    // Method to calculate sqrt of a number
    public BigDecimal sqrt(BigDecimal x) {
        BigDecimal i = BigDecimal.ONE;
        while (true) {
            if (i.multiply(i).equals(x))
                return i;
            else if (i.multiply(i).compareTo(x) > 0)
                return decimalSqrt(x, i.subtract(BigDecimal.ONE), i);
            i = i.add(BigDecimal.ONE);
        }
    }

    public BigInteger factorial(BigInteger f) {
        if (f.compareTo(BigInteger.ZERO) < 0) {
            LOGGER.error("Error: Invalid input");
            return null;
        }
        long i, fct = 1;
        for (i = 1; i <= f.longValue(); i++) {
            fct = fct * i;
        }
        return BigInteger.valueOf(fct);
    }

    /**
     * This function calculates the natural logarithm of a given value
     *
     * @param x input value
     * @return big decimal of natural log on x.
     */
    public BigDecimal ln(BigDecimal x) {

        LOGGER.debug("Subordinates: ln called on {}", x);

        // Check if the input value is valid
        if (x.compareTo(BigDecimal.ZERO) <= 0) {
            LOGGER.error("Subordinates: ln call on {} interrupted -- Failure", x);
            throw new IllegalArgumentException("ln(x): x must be positive");
        }
        //recursion to maintain small value of x.
        if (x.compareTo(BigDecimal.valueOf(10_000)) > 0) {
            // Divide the big input value by 1000 and add the natural logarithm of 1000 to the result. Used logarithm property log(ab) = log(a) + log(b)
            return ln(x.divide((BigDecimal.valueOf(1_000)), PRECISION)).add(ln1000);
        }
        // Initialize variables
        BigDecimal y = x.subtract(BigDecimal.ONE);
        BigDecimal z = y.divide(x, PRECISION);
        BigDecimal result = z;
        BigDecimal zPower = z;
        BigDecimal threshHold = new BigDecimal("1E-1000");

        // Calculate the natural logarithm using the Taylor series expansion
        for (int i = 2; i <= 100_000; i++) {
            zPower = zPower.multiply(z, PRECISION);
            BigDecimal term = zPower.divide(BigDecimal.valueOf(i), PRECISION);
            result = result.add(term, PRECISION);
            // Break the loop if the next term is smaller than the given threshold
            if (term.abs().compareTo(threshHold) < 0) {
                break;
            }
            //if the loop doesn't break, the for loop returns the value after completing 100_000 loops. The value returned in such case is not very accurate, but close.
        }
        LOGGER.debug("Subordinates: ln call on {} completed -- Success", x);
        return result;
    }

    public BigDecimal power(BigDecimal base, BigDecimal exp) {
        // handle case of 0^0
        if (base.compareTo(BigDecimal.ZERO) == 0 && exp.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ONE;
        }
        // handle case of 0^x
        else if (base.compareTo(BigDecimal.ZERO) == 0 && exp.compareTo(BigDecimal.ZERO) != 0)
            return BigDecimal.ZERO;
            // Handle case of negative base
        else if (base.compareTo(BigDecimal.ZERO) < 0) {
            try {

                if (exp.remainder(BigDecimal.valueOf(2)).compareTo(BigDecimal.ZERO) == 0)
                    return power(BigDecimal.valueOf(-1).multiply(base), exp);
                    // Negative base, odd exponent case
                else
                    return BigDecimal.valueOf(-1).multiply(power(BigDecimal.valueOf(-1).multiply(base), exp));
            } catch (ArithmeticException ex) {
                // Negative base, noninteger exponent case
                LOGGER.info("Error: Unreal solution");
                return null;
            }
        }
        // Handle case of negative exponent
        else if (exp.compareTo(BigDecimal.ZERO) < 0) {
            BigDecimal result = power(base, BigDecimal.valueOf(-1).multiply(exp));
            return BigDecimal.valueOf(1 / result.doubleValue());
        }

        // Covers prior to decimal point using the property x^y = x^(y/2)^2
        if (exp.compareTo(BigDecimal.ONE) >= 0) {
            BigDecimal temp = power(base, exp.divide(BigDecimal.valueOf(2), PRECISION));
            return temp.multiply(temp);
        } else {
            //now deal with the fractional part
            BigDecimal low = BigDecimal.ZERO;
            BigDecimal high = BigDecimal.ONE;
            BigDecimal sqr = sqrt(base);
            BigDecimal acc = sqr;
            BigDecimal mid = high.divide(BigDecimal.valueOf(2), PRECISION);
            BigDecimal error = mid.subtract(exp).abs();
            while (error.compareTo(THRESHOLD) > 0) {
                sqr = sqrt(sqr);
                if (mid.compareTo(exp) <= 0) {
                    low = mid;
                    acc = acc.multiply(sqr);
                } else {
                    high = mid;
                    acc = acc.multiply(BigDecimal.valueOf((1 / sqr.doubleValue())));
                }
                mid = low.add(high).divide(BigDecimal.valueOf(2), PRECISION);
                error = mid.subtract(exp).abs();
            }
            return acc;
        }
    }

    public BigDecimal sin(BigDecimal x) {
        BigDecimal sum = BigDecimal.valueOf(0);
        BigDecimal fact = BigDecimal.valueOf(1);
        BigDecimal pow = new BigDecimal(x.toString());
        int count = 1;
        for (int i = 0; i < 15; ++i) {
            // todo should this be sum = sum.add?
            sum = sum.add(pow.divide(fact, PRECISION));
            pow = x.multiply(x).multiply(pow).multiply(BigDecimal.valueOf(-1));
            fact = fact.multiply(BigDecimal.valueOf((long) (count + 1) * count + 2));
            count += 2;
        }
        return sum;
    }
}
