package com.softweavers.eternity.Domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class Subordinates {
    final static int NDIGITS = 10;
    private final static BigDecimal NEGATIVE_ONE = BigDecimal.ZERO.subtract(BigDecimal.ONE);
    private static final Logger LOGGER = LoggerFactory.getLogger(Subordinates.class);
    private static final MathContext PRECISION = MathContext.DECIMAL128;
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

    public BigInteger abs(BigInteger a) {
        if (a.compareTo(BigInteger.ZERO) >= 0)
            return a;
        else
            return a.multiply(BigInteger.valueOf(-1));
    }

    public BigDecimal abs(BigDecimal a) {
        if (a.compareTo(BigDecimal.ZERO) >= 0)
            return a;
        else
            return a.multiply(BigDecimal.valueOf(-1));
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

    public BigDecimal ln(BigDecimal x) {

        if (x.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("ln(x): x must be positive");
        }
        //recursion to maintain small value of x.
        if (x.compareTo(BigDecimal.valueOf(10_000)) > 0) {
            return ln(x.divide((BigDecimal.valueOf(1_000)), PRECISION)).add(ln1000);
        }

        int scale = x.precision() + 2;
        BigDecimal y = x.subtract(BigDecimal.ONE);
        BigDecimal z = y.divide(x, PRECISION);
        BigDecimal result = z;
        BigDecimal zPower = z;

        for (int i = 2; i <= 100000; i++) {
            zPower = zPower.multiply(z, PRECISION);
            BigDecimal term = zPower.divide(new BigDecimal(i), PRECISION);
            result = result.add(term, PRECISION);

            if (term.abs().compareTo(new BigDecimal("1E-1000")) < 0) {
                break;
            }
        }

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
                BigDecimal test = exp.remainder(BigDecimal.valueOf(2));
                if (exp.remainder(BigDecimal.valueOf(2)).compareTo(BigDecimal.ZERO) == 0)
                    return power(NEGATIVE_ONE.multiply(base), exp);
                    // Negative base, odd exponent case
                else
                    return NEGATIVE_ONE.multiply(power(NEGATIVE_ONE.multiply(base), exp));
            } catch (ArithmeticException ex) {
                // Negative base, noninteger exponent case
                LOGGER.info("Error: Unreal solution");
                return null;
            }
        }
        // Handle case of negative exponent
        else if (exp.compareTo(BigDecimal.ZERO) < 0) {
            BigDecimal result = power(base, NEGATIVE_ONE.multiply(exp));
            return new BigDecimal(1 / result.doubleValue());
        }

        // Covers prior to decimal point using the property x^y = x^(y/2)^2
        BigDecimal temp = new BigDecimal(0);
        if (exp.compareTo(BigDecimal.ONE) >= 0) {
            temp = power(base, exp.divide(BigDecimal.valueOf(2)));
            return temp.multiply(temp);
        } else {
            //now deal with the fractional part
            BigDecimal low = BigDecimal.ZERO;
            BigDecimal high = BigDecimal.ONE;
            BigDecimal sqr = sqrt(base);
            BigDecimal acc = sqr;
            BigDecimal mid = high.divide(BigDecimal.valueOf(2));
            BigDecimal error = mid.subtract(exp).abs();
            while (error.compareTo(THRESHOLD) > 0) {
                sqr = sqrt(sqr);
                if (mid.compareTo(exp) <= 0) {
                    low = mid;
                    acc = acc.multiply(sqr);
                } else {
                    high = mid;
                    acc = acc.multiply(new BigDecimal(1 / sqr.doubleValue()));
                }
                mid = low.add(high).divide(BigDecimal.valueOf(2));
                error = mid.subtract(exp).abs();
            }
            return acc;
        }
    }

    public BigDecimal sin(BigDecimal x) {
        BigDecimal sum = new BigDecimal(0);
        BigDecimal fact = new BigDecimal(1);
        BigDecimal pow = new BigDecimal(x.toString());
        int count = 1;
        for (int i = 0; i < 15; ++i) {
            // todo should this be sum = sum.add?
            sum = sum.add(pow.divide(fact, PRECISION));
            pow = x.multiply(x).multiply(pow).multiply(new BigDecimal(-1));
            fact = fact.multiply(new BigDecimal((count + 1) * count + 2));
            count += 2;
        }
        return sum;
    }

    //todo why do we have this? we already have a sqrt
//    public double sqrt(double x) {
//        int i = 1;
//        while (true) {
//            //for perfect square numbers
//            if (i * i == x)
//                return i;
//                //for not perfect square numbers
//            else if (i * i > x)
//                //returns the value calculated by the method decimalSqrt()
//                return decimalSqrt(x, i - 1, i);
//            i++;
//        }
//    }

    //todo why do we have this double?
    // recursive method to find the square root of a number up to n decimal places
//    private double decimalSqrt(double number, double i, double j) {
//        // using Newton's method to find a value close to the real value
//        double midvalue = (i + j) / 2;
//        double square = midvalue * midvalue;
//        //compares the midvalue with square. Accuracy is set to 10 decimal places.
//        if (square == number || abs(BigInteger.valueOf(square - number)) < 0.0000000001)
//            return midvalue;
//            //if the square root belongs to second half
//        else if (square > number)
//            return decimalSqrt(number, i, midvalue);
//            //if the square root belongs to first half
//        else
//            return decimalSqrt(number, midvalue, j);
//    }

    // same principle with decimalSqrt function using Newton's method
//    public double pow(double base, double exp) {
//        double temp = 0;
//        //covers prior to decimal point using the property x^y = x^(y/2)^2
//        if (exp >= 1) {
//            temp = pow(base, exp / 2);
//            return temp * temp;
//        }
//        //now deal with the fractional part
//        else {
//            double low = 0;
//            double high = 1.0;
//            double sqr = sqrt(base);
//            double acc = sqr;
//            double mid = high / 2;
//
//            // Accuracy is set to 10 decimal points
//            while (abs(mid - exp) > 0.0000000001) {
//                sqr = sqrt(sqr);
//
//                if (mid <= exp) {
//                    low = mid;
//                    acc *= sqr;
//                } else {
//                    high = mid;
//                    acc *= (1 / sqr);
//                }
//                mid = (low + high) / 2;
//            }
//            return acc;
//        }
////    }
//    public BigDecimal nthroot(BigDecimal n, BigDecimal x) {
//        // if x is negative, returns error message
//        if (x.compareTo(BigDecimal.ZERO) < 0) {
//            System.err.println("Invalid input: Negative!");
//            return NEGATIVE_ONE;
//        }
//        if (x.compareTo(BigDecimal.ZERO) == 0)
//            return BigDecimal.ZERO;
//        BigDecimal x1 = x;
//        BigDecimal x2 = x.divide(n,PRECISION);
//        BigDecimal x3 = x1.subtract(x2);
//        if (x3.compareTo(BigDecimal.ZERO) < 0)
//            x3 = x3.multiply(NEGATIVE_ONE);
//        while (x3.compareTo(BigDecimal.valueOf(0.0000000001))>0) {
//            x1 = x2;
//            x2 = ((n.subtract(BigDecimal.ONE)).multiply(x2).add( x.divide(power(x2, n.subtract(BigDecimal.ONE)),PRECISION)).divide(n,PRECISION);
//        }
//        return x2;
//    }

    //todo is this needed? no domain validation
    public BigDecimal cosh(BigDecimal x) {
        BigDecimal e = BigDecimal.valueOf(Math.E);
        BigDecimal pow1 = power(e, x);
        BigDecimal pow2 = BigDecimal.ONE.divide(pow1, PRECISION);

        return (pow1.add(pow2)).divide(BigDecimal.valueOf(2), PRECISION);
    }
}