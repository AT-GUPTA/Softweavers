package com.softweavers.eternity.Domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;


public class FunctionsImpl implements FunctionHandler {

    public static final MathContext PRECISION = MathContext.DECIMAL128;
    private static final Logger LOGGER = LoggerFactory.getLogger(FunctionsImpl.class);
    //todo what is lanczos?
    private static final BigDecimal[] lanczos = {new BigDecimal("676.5203681218851"),
            new BigDecimal("-1259.1392167224028"), new BigDecimal("771.32342877765313"),
            new BigDecimal("-176.61502916214059"), new BigDecimal("12.507343278686905"),
            new BigDecimal("-0.13857109526572012"), new BigDecimal("9.9843695780195716e-6"), new BigDecimal(
            "1.5056327351493116e-7")};
    private final Subordinates subordinates = new Subordinates();

    /**
     * Computes the hyperbolic sine of a given BigDecimal.
     *
     * @param x the argument for the hyperbolic sine function
     * @return the hyperbolic sine of x
     */
    @Override
    public BigDecimal sinh(BigDecimal x) {
        // compute e^x and 1/e^x
        BigDecimal e = BigDecimal.valueOf(Math.E);
        BigDecimal pow1 = subordinates.power(e, x);
        BigDecimal pow2 = BigDecimal.ONE.divide(pow1, MathContext.DECIMAL128);
        //Decima1128 for accurity of 34 decimal places.

        // compute sinh(x) = (e^x - 1/e^x) / 2
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

    private BigDecimal bd(double x) {
        return new BigDecimal(x);
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
            BigDecimal[] args = new BigDecimal[2];
            args[0] = t;
            args[1] = z.add(new BigDecimal(0.5));
            y = bd(Math.pow(2 * Math.PI, 0.5))
                    .multiply(pow(args))
                    .multiply(bd(Math.pow(Math.E, t.doubleValue() * -1)))
                    .multiply(x);
        }
        return y.round(PRECISION);
    }

    /**
     * A method to compute the function arccos(x) using Taylor's Series for up to n = 16
     *
     * @param x a BigDecimal value between -1 and 1
     * @return a Bigdecimal value, arccos of the function in degrees.
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
                        (subordinates.power(BigDecimal.valueOf(2), BigDecimal.valueOf(2L * n)).multiply(subordinates.power(bdFactorial, BigDecimal.valueOf(2)))), MathContext.DECIMAL128);
                fraction2 = subordinates.power(x, BigDecimal.valueOf((2L * n) + 1)).divide(BigDecimal.valueOf((2L * n) + 1), MathContext.DECIMAL128);
                loop_result = loop_result.add(fraction1.multiply(fraction2));
            }
            return BigDecimal.valueOf(Math.PI / 2).subtract(loop_result, MathContext.DECIMAL128);
        }
    }

    @Override
    public BigDecimal log(BigDecimal[] values) {
        if (values.length != 2)
            throw new IllegalArgumentException("Log function requires 2 inputs.");

        BigDecimal value = values[0];
        BigDecimal base = values[1];

        if (base.compareTo(BigDecimal.ONE) == 0 || value.compareTo(BigDecimal.ZERO) <= 0 || base.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Base and value must be greater than 1");
        }
        BigDecimal decimal = subordinates.ln(value).divide(subordinates.ln(base), PRECISION);

        return decimal.setScale(10, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal xToY(BigDecimal[] values) {
        if (values.length == 1){
            BigDecimal base = BigDecimal.valueOf(BigDecimal.ZERO);
            BigDecimal exp = values[0];
        }
        else {
        BigDecimal base = values[0];
        BigDecimal exp = values[1];
        }
        if (base.compareTo(BigDecimal.ZERO) < 0)
            try {
                BigDecimal test = exp.remainder(BigDecimal.valueOf(2));
                if (exp.remainder(BigDecimal.valueOf(2)).compareTo(BigDecimal.ZERO) == 0)
                    return subordinates.power(NEGATIVE_ONE.multiply(base), exp);
                else
                    return NEGATIVE_ONE.multiply(subordinates.power(NEGATIVE_ONE.multiply(base), exp));
            } catch (ArithmeticException ex) {
                LOGGER.info("Error: Unreal solution");
                return null;
            }
        return subordinates.power(base, exp);
    }

    @Override
    public BigDecimal abx(BigDecimal a, BigDecimal b, BigDecimal x) {
        return a.multiply(subordinates.power(b, x));
    }

    public BigDecimal standardDeviation(BigDecimal[] values) {
        if (values.length < 2)
            throw new IllegalArgumentException("Standard Deviation requires more than 2 inputs");

        BigDecimal mean = calculateMean(values);

        BigDecimal standardDev = BigDecimal.valueOf(0);
        for (BigDecimal value : values) {
            standardDev = standardDev.add(subordinates.power(value.subtract(mean), BigDecimal.valueOf(2)));
        }

        standardDev = standardDev.divide(new BigDecimal(values.length), PRECISION);
        standardDev = subordinates.power(standardDev, BigDecimal.valueOf(.5));
        return standardDev;
    }

    private BigDecimal calculateMean(BigDecimal[] values) {
        BigDecimal sum = new BigDecimal(0);
        for (BigDecimal value : values) {
            sum = sum.add(value);
        }

        return sum.divide(BigDecimal.valueOf(values.length), PRECISION);
    }
}
