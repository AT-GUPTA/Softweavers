package com.softweavers.eternity.Domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;


public class FunctionsImpl implements FunctionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FunctionsImpl.class);
    public static final MathContext PRECISION = MathContext.DECIMAL128;

    //todo what is lanczos?
    private static final BigDecimal[] lanczos = {new BigDecimal("676.5203681218851"),
            new BigDecimal("-1259.1392167224028"), new BigDecimal("771.32342877765313"),
            new BigDecimal("-176.61502916214059"), new BigDecimal("12.507343278686905"),
            new BigDecimal("-0.13857109526572012"), new BigDecimal("9.9843695780195716e-6"), new BigDecimal(
            "1.5056327351493116e-7")};
    private final Subordinates subordinates = new Subordinates();

    //todo no domain validation?
    @Override
    public BigDecimal sinh(BigDecimal x) {
        BigDecimal e = BigDecimal.valueOf(Math.E);
        BigDecimal pow1 = subordinates.power(e, x);
        BigDecimal pow2 = BigDecimal.ONE.divide(pow1, MathContext.DECIMAL128);
        //Decima1128 for accurity of 34 decimal places.

        return (pow1.subtract(pow2)).divide(BigDecimal.valueOf(2), MathContext.DECIMAL128);
    }

    @Override
    public BigDecimal pow(BigDecimal[] values) {
        if (values.length != 2)
            throw new IllegalArgumentException("Power function requires 2 inputs.");
        BigDecimal base = values[0];
        BigDecimal exp = values[1];

        return subordinates.power(base, exp);
    }

    // Calculate the Gamma function for a given input value
    @Override
    public BigDecimal gamma(BigDecimal z) {
        if (z.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Input must be positive");
        }
        BigDecimal y = null;
        if (z.compareTo(new BigDecimal("0.5")) < 0) {
            BigDecimal a = z.multiply(new BigDecimal(Math.PI));
            BigDecimal b = new BigDecimal(1).subtract(z);
            y = new BigDecimal(Math.PI).divide(subordinates.sin(a), MathContext.DECIMAL128).multiply(gamma(b), PRECISION);
        } else {
            z = z.subtract(new BigDecimal(1));
            BigDecimal x = new BigDecimal("0.99999999999980993");
            for (int i = 0; i < lanczos.length; ++i) {
                x = x.add(lanczos[i].divide(z.add(new BigDecimal(i + 1)), PRECISION));
            }
            BigDecimal t = z.add(new BigDecimal(lanczos.length - 0.5));
            double as = Math.pow(2 * Math.PI, 0.5) * Math.pow(t.doubleValue(), z.doubleValue() + 0.5)
                    * Math.pow(Math.E, t.doubleValue() * -1) * x.doubleValue();
            y = new BigDecimal(as);
        }
        return y;
    }

        /**
     * A method to compute the function arccos(x) using Taylor's Series for up to n = 16
     * @param x a BigDecimal value between -1 and 1
     * @return
     */
    @Override
    public BigDecimal arccos(BigDecimal x) {
        if (x.compareTo(BigDecimal.valueOf(1)) > 0 || x.compareTo(BigDecimal.valueOf(-1)) < 0)
            throw new IllegalArgumentException("Input for arccos(x) out of domain.");
        else if (x.compareTo(BigDecimal.valueOf(1)) == 0)
            return (BigDecimal.valueOf(0.0));
        else if (x.compareTo(BigDecimal.valueOf(-1)) == 0)
            return (BigDecimal.valueOf(Math.PI));
        else {
            BigDecimal loop_result = new BigDecimal(0);
            BigDecimal fraction1, fraction2;
            int end = 16;
            for (int n = 0; n <= end; n++) {
                //for use in power function in denominator for fraction1
                BigDecimal bdFactorial = new BigDecimal(subordinates.factorial(BigInteger.valueOf(n)));
                //numerator of fraction 1 in Big Decimal
                BigDecimal fraction1Numerator = new BigDecimal(subordinates.factorial(BigInteger.valueOf(2L * n)));
                fraction1 = fraction1Numerator.divide(
                        (subordinates.power(BigDecimal.valueOf(2), BigDecimal.valueOf(2 * n)).multiply(subordinates.power(bdFactorial, BigDecimal.valueOf(2)))), MathContext.DECIMAL128);
                fraction2 = subordinates.power(x, BigDecimal.valueOf((2L * n) + 1)).divide(BigDecimal.valueOf((2L * n) + 1), MathContext.DECIMAL128);
                loop_result = loop_result.add(fraction1.multiply(fraction2));
            }
            return BigDecimal.valueOf(Math.PI/2).subtract(loop_result, MathContext.DECIMAL128);
        }

    @Override
    public BigDecimal log(BigDecimal[] values) {
        if (values.length != 2)
            throw new IllegalArgumentException("Log function requires 2 inputs.");

        BigDecimal val = values[0];
        BigDecimal base = values[1];

        if (val.compareTo(BigDecimal.ZERO) <= 0 || base.compareTo(BigDecimal.ZERO) <= 0 || base.compareTo(BigDecimal.ONE) == 0) {
            throw new IllegalArgumentException("Invalid input");
        }

        int sign = 1;
        if (val.compareTo(BigDecimal.ONE) < 0) {
            val = BigDecimal.ONE.divide(val, MathContext.DECIMAL128);
            sign = -1;
        }

        BigDecimal result = BigDecimal.ZERO;
        while (val.compareTo(base.multiply(base)) >= 0) {
            BigDecimal temp = subordinates.logHelper(base);
            int power = (subordinates.logHelper(val)).divide(temp, RoundingMode.DOWN).intValue();
            result = result.add(BigDecimal.valueOf(power));
            val = val.divide(base.pow(power), MathContext.DECIMAL128);
        }

        BigDecimal term = val.subtract(BigDecimal.ONE).divide(base, MathContext.DECIMAL128);
        BigDecimal numerator = BigDecimal.valueOf(-1);
        int denominator = 2;
        while (term.compareTo(BigDecimal.ZERO) != 0) {
            result = result.add(term);
            numerator = numerator.multiply(val.subtract(BigDecimal.ONE));
            term = numerator.divide(BigDecimal.valueOf(denominator).multiply(base.pow(denominator - 1)), MathContext.DECIMAL128);
            denominator++;
        }

        return BigDecimal.valueOf(sign).multiply(result);
    }

    @Override
    public BigDecimal xToY(BigDecimal y) {
        BigDecimal x = BigDecimal.valueOf(0);
        return subordinates.power(x, y);
    }

    public BigDecimal standardDeviation(BigDecimal[] values){
        if (values.length < 2)
            throw new IllegalArgumentException("Standard Deviation requires more than 2 inputs");

        BigDecimal mean = calculateMean(values);

        BigDecimal standardDev = BigDecimal.valueOf(0);
        for (BigDecimal value : values){
            standardDev = standardDev.add(subordinates.power(value.subtract(mean), BigDecimal.valueOf(2)));
        }

        standardDev = standardDev.divide(new BigDecimal(values.length), PRECISION);
        standardDev = subordinates.power(standardDev, BigDecimal.valueOf(.5));
        return standardDev;
    }
    private BigDecimal calculateMean(BigDecimal[] values){
        BigDecimal sum = new BigDecimal(0);
        for (BigDecimal value : values){
            sum = sum.add(value);
        }

        return sum.divide(BigDecimal.valueOf(values.length), PRECISION);
    }
}
