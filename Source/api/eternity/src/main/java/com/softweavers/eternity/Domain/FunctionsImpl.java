package com.softweavers.eternity.Domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;


public class FunctionsImpl implements FunctionHandler {

    public static final MathContext PRECISION = MathContext.DECIMAL128;
    private static final Logger LOGGER = LoggerFactory.getLogger(FunctionsImpl.class);
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

    // Calculate the Gamma function for a given input value
    @Override
    public BigDecimal gamma(BigDecimal z) {
        // check if input value is positive
        if (z.compareTo(BigDecimal.ZERO) <= 0) {
            LOGGER.error("FunctionImpl: gamma called with invalid input -- Failure");
            throw new IllegalArgumentException("Input must be positive");
        }
        LOGGER.debug("FunctionImpl: gamma call input verified");
        BigDecimal y;
        // if input is less than 0.5, use Euler's reflection formula
        if (z.compareTo(new BigDecimal("0.5")) < 0) {
            BigDecimal a = z.multiply(new BigDecimal(Math.PI));
            BigDecimal b = new BigDecimal(1).subtract(z);
            // using recursion to calculate gamma function
            y = new BigDecimal(Math.PI).divide(subordinates.sin(a), MathContext.DECIMAL128).multiply(gamma(b), PRECISION);
        } else {
            // if the input value is greater than or equal to 0.5, use the Lanczos approximation
            z = z.subtract(new BigDecimal(1));
            BigDecimal x = new BigDecimal("0.99999999999980993");
            // computing lanczos approximation by using a loop
            for (int i = 0; i < lanczos.length; ++i) {
                // computing the next term in the Lanczos approximation
                x = x.add(lanczos[i].divide(z.add(new BigDecimal(i + 1)), PRECISION));
            }

            // computing the argument for the gamma function in the Lanczos approximation
            BigDecimal t = z.add(new BigDecimal(lanczos.length - 0.5));
            BigDecimal[] args = new BigDecimal[2];
            args[0] = t;
            args[1] = z.add(new BigDecimal("0.5"));
            // computing gamma function using the Lanczos approximation
            y = subordinates.power(new BigDecimal(2 * Math.PI), new BigDecimal("0.5"))
                    .multiply(subordinates.power(args[0], args[1]))
                    .multiply(subordinates.power(new BigDecimal(Math.E), t.multiply(new BigDecimal(-1))))
                    .multiply(x);
        }
        // rounding result
        y = y.setScale(12, RoundingMode.HALF_UP);
        LOGGER.debug("FunctionImpl: gamma function output: " + y);
        // returning result
        return y;
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
        LOGGER.debug("FunctionsImpl: log called on Object {}", Arrays.toString(values));
        // Check if there are two input values
        if (values.length != 2) {
            LOGGER.error("FunctionImpl: log called with invalid input -- Failure");
            throw new IllegalArgumentException("Log function requires 2 inputs.");
        }
        // Get the input values
        BigDecimal value = values[0];
        BigDecimal base = values[1];

        // Check if the input values are valid
        if (base.compareTo(BigDecimal.ONE) == 0 || value.compareTo(BigDecimal.ZERO) <= 0 || base.compareTo(BigDecimal.ZERO) <= 0) {
            LOGGER.error("FunctionImpl: log called with invalid input -- Failure");
            throw new IllegalArgumentException("Base and value must be greater than 1");
        }
        LOGGER.debug("FunctionImpl: log call inputs verified");
        // Calculate the logarithm using natural logarithm and return the result. Used base change theorem log(b)x = (lnx)/(lnb)
        BigDecimal decimal = subordinates.ln(value).divide(subordinates.ln(base), PRECISION);
        LOGGER.debug("FunctionImpl: log call for value {} and base {} completed -- Success", value, base);
        // returning the result with 12 decimal places.
        return decimal.setScale(12, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal xToY(BigDecimal[] values) {
        if (values.length != 2)
            throw new IllegalArgumentException("Requires 2 inputs");

        BigDecimal base = values[0];
        BigDecimal exp = values[1];

        return subordinates.power(base, exp);
    }

    @Override
    public BigDecimal abx(BigDecimal[] values) {
        if (values.length != 3)
            throw new IllegalArgumentException("abx function requires 3 inputs.");

        BigDecimal a = values[0];
        BigDecimal b = values[1];
        BigDecimal x = values[2];

        return a.multiply(subordinates.power(b, x));
    }

    public BigDecimal standardDeviation(BigDecimal[] values) {
        // Check to see if there is a sufficient number of values
        if (values.length < 2)
            throw new IllegalArgumentException("Standard Deviation requires more than 2 inputs");

        // Get the mean of the collection of input values
        BigDecimal mean = calculateMean(values);

        // Compound (mean-value)^2
        BigDecimal standardDev = BigDecimal.valueOf(0);
        for (BigDecimal value : values) {
            standardDev = standardDev.add(subordinates.power(value.subtract(mean), BigDecimal.valueOf(2)));
        }

        // Divide by the total number of inputs
        standardDev = standardDev.divide(new BigDecimal(values.length), PRECISION);

        // Return the square root
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
