package com.softweavers.eternity.Domain;
import com.softweavers.eternity.Service.CalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public class Functions {
    private final Subordinates subordinates = new Subordinates();
    final static int NDIGITS = 10;
    final static BigDecimal THRESHOLD = BigDecimal.ONE.divide(BigDecimal.TEN.pow(2 * NDIGITS));
    final static BigDecimal NEGATIVE_ONE = BigDecimal.ZERO.subtract(BigDecimal.ONE);
    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorService.class);


    public BigDecimal arccos(BigDecimal x) {
        if (x.compareTo(BigDecimal.valueOf(1)) == 1 || x.compareTo(BigDecimal.valueOf(-1)) == -1)
            throw new IllegalArgumentException("Input for arccos(x) out of domain.");
        else if (x.compareTo(BigDecimal.valueOf(1)) == 0)
            return (BigDecimal.valueOf(0.0));
        else if (x.compareTo(BigDecimal.valueOf(-1)) == 0)
            return (BigDecimal.valueOf(Math.PI));
        else {
            BigDecimal loop_result = new BigDecimal(0);
            BigDecimal fraction1, fraction2;
            int end = 30;
            for (int n = 0; n <= end; n++) {
                //for use in power function in denominator for fraction1
                BigDecimal bdFactorial = new BigDecimal(subordinates.factorial(BigInteger.valueOf(n)));
                //numerator of fraction 1 in Big Decimal
                BigDecimal fraction1Numerator = new BigDecimal(subordinates.factorial(BigInteger.valueOf(2*n)));
                fraction1 = fraction1Numerator.divide(
                        (pow(BigDecimal.valueOf(2), BigDecimal.valueOf(2 * n)).multiply(pow(bdFactorial, BigDecimal.valueOf(2)))));
                fraction2 = pow(x, BigDecimal.valueOf((2 * n) + 1)).divide(BigDecimal.valueOf((2 * n) + 1));
                loop_result = loop_result.add(fraction1.multiply(fraction2));
            }
            BigDecimal result = BigDecimal.valueOf(Math.PI).divide(loop_result);
            return result;
        }
    }	

    public BigDecimal logarithm(BigDecimal val, BigDecimal base) {
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

    public BigDecimal sinh(BigDecimal x) {
        BigDecimal e = BigDecimal.valueOf(Math.E);
        BigDecimal pow1 = pow(e, x);
        BigDecimal pow2 = BigDecimal.ONE.divide(pow1, MathContext.DECIMAL128);
        //Decima1128 for accurity of 34 decimal places.
        BigDecimal result = (pow1.subtract(pow2)).divide(BigDecimal.valueOf(2), MathContext.DECIMAL128);

        return result;
    }

    public BigDecimal cosh(BigDecimal x) {
        BigDecimal e = BigDecimal.valueOf(Math.E);
        BigDecimal pow1 = pow(e, x);
        BigDecimal pow2 = BigDecimal.ONE.divide(pow1, MathContext.DECIMAL128);
        BigDecimal result = (pow1.add(pow2)).divide(BigDecimal.valueOf(2), MathContext.DECIMAL128);

        return result;
    }
	
	public BigDecimal pow(BigDecimal base, BigDecimal exp) {
		// Handle case of negative base
		if (base.compareTo(BigDecimal.ZERO) < 0) {
			try {
		        BigInteger expInt = exp.toBigIntegerExact();
		        // Negative base, even integer exponent case
		        if (expInt.mod(BigInteger.valueOf(2)) == BigInteger.ZERO)
		        	return pow(NEGATIVE_ONE.multiply(base), exp);
		        // Negative base, odd exponent case
		        else
		        	return NEGATIVE_ONE.multiply(pow(NEGATIVE_ONE.multiply(base), exp));
		    } catch (ArithmeticException ex) {
		    	// Negative base, noninteger exponent case
		        LOGGER.info("Error: Unreal solution");
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
			temp = pow(base, exp.divide(BigDecimal.valueOf(2)));
			return temp.multiply(temp).round(new MathContext(NDIGITS));
		} else {
			//now deal with the fractional part 
			BigDecimal low = BigDecimal.ZERO;
			BigDecimal high = BigDecimal.ONE;
			BigDecimal sqr = subordinates.sqrt(base);
			BigDecimal acc = sqr;
			BigDecimal mid = high.divide(BigDecimal.ONE);
			BigDecimal error = mid.subtract(exp).abs();
			while (error.compareTo(THRESHOLD) > 0) {
				sqr = subordinates.sqrt(sqr);
				if (mid.compareTo(exp) <= 0) {
					low = mid;
					acc = acc.multiply(sqr);
				} else {
					high = mid;
					acc = acc.multiply(new BigDecimal(1/sqr.doubleValue()));
				}
				mid = low.add(high).divide(BigDecimal.valueOf(2));
				error = mid.subtract(exp).abs();
			}
			return acc.round(new MathContext(NDIGITS));
		}
	}    

	public BigDecimal XtoY(BigDecimal y){
        	BigDecimal x = BigDecimal.valueOf(0);
      	 	// if (previousResult != null)
       		// x = previousResult;
       		BigDecimal result = pow(x,y);
       		return result;
   	}
	
    private static final MathContext PRECISION = MathContext.DECIMAL128;

    private BigDecimal sin(BigDecimal x) {
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

    private BigDecimal[] lanczos = { new BigDecimal("676.5203681218851"),
            new BigDecimal("-1259.1392167224028"), new BigDecimal("771.32342877765313"),
            new BigDecimal("-176.61502916214059"), new BigDecimal("12.507343278686905"),
            new BigDecimal("-0.13857109526572012"), new BigDecimal("9.9843695780195716e-6"), new BigDecimal(
                    "1.5056327351493116e-7") };

    // Calculate the Gamma function for a given input value
    public BigDecimal gamma(BigDecimal z) {
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
            for (int i = 0; i <  lanczos.length; ++i) {
                x = x.add(lanczos[i].divide(z.add(new BigDecimal(i + 1)), PRECISION));
            }
            BigDecimal t = z.add(new BigDecimal(lanczos.length - 0.5));
            double as = Math.pow(2 * Math.PI, 0.5) * Math.pow(t.doubleValue(), z.doubleValue() + 0.5)
                    * Math.pow(Math.E, t.doubleValue() * -1) * x.doubleValue();
            y = new BigDecimal(as);
        }
        return y;
    }
}
