package com.softweavers.eternity.Domain;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Arrays;

import static com.softweavers.eternity.Domain.FunctionParser.mc;

public class Functions {
    private final Subordinates subordinates = new Subordinates();
    final static int NDIGITS = 10;
    final static BigDecimal THRESHOLD = BigDecimal.ONE.divide(BigDecimal.TEN.pow(2 * NDIGITS));
    final static BigDecimal NEGATIVE_ONE = BigDecimal.ZERO.subtract(BigDecimal.ONE);
    
     /*
    taylor series for arccos = pi/2 - taylor series for sin
    for loop will calculate taylor series for sin
    trial and error to find right number of n
    final result will be pi/2 - result from for loop
    return result converted to big decimal
     */
    public static BigDecimal arccos(BigDecimal x) {
        if (x.compareTo(BigDecimal.valueOf(1)) == 1 || x.compareTo(BigDecimal.valueOf(-1)) == -1)
            throw new IllegalArgumentException("Input for arccos(x) out of domain.");
        else if (x.compareTo(BigDecimal.valueOf(1)) == 0)
            return (BigDecimal.valueOf(0.0));
        else if (x.compareTo(BigDecimal.valueOf(0)) == 0)
            return (BigDecimal.valueOf(Math.PI));
        else {
            BigDecimal loop_result = new BigDecimal(0);
            BigDecimal fraction1, fraction2;
            int end = 30;
            for (int n = 0; n <= end; n++) {
                fraction1 = factorial(BigInt.valueOf(2*n)) /
                    (pow(BigInt.valueOf(2), BigInt.valueOf(2 * n)) * pow(factorial(BigInt.valueOf(n)), BigInt.valueOf(2)));
                fraction2 = pow(BigDecimal.valueOf(x), BigInt.valueOf((2 * n) + 1))/
                 BigInt.valueOf((2 * n) + 1);
                loop_result = loop_result.add(fraction1.multiply(fraction2));
            }
            BigDecimal result = BigDecimal.valueOf(Math.PI).divide(loop_result);
            return result;
        }
    }

    public double logarithm(double val, double base) {
        if (val <= 0 || base <= 0 || base == 1) {
            throw new IllegalArgumentException("Invalid input");
        }

        int sign = 1;
        if (val < 1) {
            val = 1 / val;
            sign = -1;
        }

        double result = 0;
        while (val >= base * base) {
            double temp = subordinates.logHelper(base);
            int power = (int) (subordinates.logHelper(val) / temp);
            result += power;
            val /= subordinates.pow(base, power);
        }

        double term = (val - 1) / base;
        double numerator = -1;
        int denominator = 2;
        while (term != 0) {
            result += term;
            numerator *= -1 * (val - 1);
            term = numerator / (denominator * subordinates.pow(base, denominator - 1));
            denominator++;
        }

        return sign * result;
    }
    
    public BigDecimal sinh(double x) {
		
        double pow1 = pow(Math.E,x);
        //get the x^y
        double pow2 = 1/pow1;
        //since x^-y  = 1/ (x^y) , we can get e^-x by 1/(e^x)  
        double result = (pow1-pow2)/2;
        //find the result by sinh(x) = (e^x  - e^-x) / 2
        BigDecimal bigDecimalValue = new BigDecimal(Double.toString(result));
        return bigDecimalValue; 
    }
	
    public BigDecimal cosh(double x) {

    	double pow1 = pow(Math.E,x);
	//get the x^y
	double pow2 = 1/pow1;
	//since x^-y  = 1/ (x^y) , we can get e^-x by 1/(e^x)  
	double result = (pow1+pow2)/2;
	//find the result by sinh(x) = (e^x  + e^-x) / 2
        BigDecimal bigDecimalValue = new BigDecimal(Double.toString(result));
        return bigDecimalValue; 
    }
	
	public static BigDecimal pow(BigDecimal base, BigDecimal exp) {
		// Handle case of negative base
		if (base.compareTo(BigDecimal.ZERO) < 0) {
			try {
		        BigInteger expInt = exp.toBigIntegerExact();
		        // Negative base, even integer exponent case
		        if (expInt.mod(BigInteger.TWO) == BigInteger.ZERO)
		        	return pow(NEGATIVE_ONE.multiply(base), exp);
		        // Negative base, odd exponent case
		        else
		        	return NEGATIVE_ONE.multiply(pow(NEGATIVE_ONE.multiply(base), exp));
		    } catch (ArithmeticException ex) {
		    	// Negative base, noninteger exponent case
		        Main.LOGGER.info("Error: Unreal solution");
		        return null;
		    }
		}
		// Handle case of negative exponent
		else if (exp.compareTo(BigDecimal.ZERO) < 0) {
			BigDecimal result = pow(base, NEGATIVE_ONE.multiply(exp)).round(new MathContext(NDIGITS));
			return new BigDecimal(1/result.doubleValue()).round(new MathContext(NDIGITS));
		}
		
		// Covers prior to decimal point using the property x^y = x^(y/2)^2
		BigDecimal temp = new BigDecimal(0);
		if (exp.compareTo(BigDecimal.ONE) >= 0) {
			temp = pow(base, exp.divide(BigDecimal.TWO));
			return temp.multiply(temp).round(new MathContext(NDIGITS));
		} else {
			//now deal with the fractional part 
			BigDecimal low = BigDecimal.ZERO;
			BigDecimal high = BigDecimal.ONE;
			BigDecimal sqr = sqrt(base);
			BigDecimal acc = sqr;
			BigDecimal mid = high.divide(BigDecimal.TWO);
			BigDecimal error = mid.subtract(exp).abs();
			while (error.compareTo(THRESHOLD) > 0) {
				sqr = sqrt(sqr);
				if (mid.compareTo(exp) <= 0) {
					low = mid;
					acc = acc.multiply(sqr);
				} else {
					high = mid;
					acc = acc.multiply(new BigDecimal(1/sqr.doubleValue()));
				}
				mid = low.add(high).divide(BigDecimal.TWO);
				error = mid.subtract(exp).abs();
			}
			return acc.round(new MathContext(NDIGITS));
		}
	}    

	public BigDecimal XtoY(BigDecimal y){
        	BigDecimal x = 0;
      	 	// if (previousResult != null)
       		// x = previousResult;
       		BigDecimal result = pow(x,y);
       		return result;
   	}
	
    private static final MathContext PRECISION = MathContext.DECIMAL128;

    private static BigDecimal sin(BigDecimal x) {
        BigDecimal sum = new BigDecimal(0);
        BigDecimal fact = new BigDecimal(1);
        BigDecimal pow = new BigDecimal(x.toString());
        int count = 1;
        for (int i = 0; i < 15; ++i) {
            sum.add(pow.divide(fact));
            pow = x.multiply(x).multiply(pow).multiply(new BigDecimal(-1));
            fact = fact.multiply(new BigDecimal((count + 1) * count + 2));
            count += 2;
        }
        return sum;
    }

    private static BigDecimal[] lanczos = { new BigDecimal("676.5203681218851"),
            new BigDecimal("-1259.1392167224028"), new BigDecimal("771.32342877765313"),
            new BigDecimal("-176.61502916214059"), new BigDecimal("12.507343278686905"),
            new BigDecimal("-0.13857109526572012"), new BigDecimal("9.9843695780195716e-6"), new BigDecimal(
                    "1.5056327351493116e-7") };

    // Calculate the Gamma function for a given input value
    public static BigDecimal gamma(BigDecimal z) {
        if (z.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Input must be positive");
        }
        BigDecimal y = null;
        if (z.compareTo(new BigDecimal(0.5)) == -1) {
            BigDecimal a = z.multiply(new BigDecimal(Math.PI));
            BigDecimal b = new BigDecimal(1).subtract(z);
            y = new BigDecimal(Math.PI).divide(sin(a)).multiply(gamma(b), PRECISION);
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
