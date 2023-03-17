package com.softweavers.eternity.Domain;

import java.math.BigDecimal;
import java.math.BigInteger;

import static com.softweavers.eternity.Domain.Functions.THRESHOLD;

public class Subordinates {

    public BigInteger abs(BigInteger a) {
        if (a.intValue() >= 0)
            return a;
        else
            return a.negate();
    }

    public BigDecimal abs(BigDecimal a) {
        if (a.doubleValue() >= 0)
            return a;
        else
            return a.negate();
    }

    public BigInteger factorial(BigInteger f) {
        if (f.intValue() < 0){
//	   Main.LOGGER.info("Error: Invalid input");
	   return null;
	}
	BigInteger fct = BigInteger.valueOf(1);
        for (int i = 1; i <= f.intValue(); i++) {
            fct = fct.multiply(BigInteger.valueOf(i));
        }
        return fct;
    }

    public double sqrt(double x) {
        int i = 1;
        while (true) {
            //for perfect square numbers
            if (i * i == x)
                return i;
                //for not perfect square numbers
            else if (i * i > x)
                //returns the value calculated by the method decimalSqrt()
                return decimalSqrt(x, i - 1, i);
            i++;
        }
    }

    // recursive method to find the square root of a number up to n decimal places    
    private double decimalSqrt(double number, double i, double j) {
        // using Newton's method to find a value close to the real value
        double midvalue = (i + j) / 2;
        double square = midvalue * midvalue;
        //compares the midvalue with square. Accuracy is set to 10 decimal places.   
        if (square == number || abs(BigDecimal.valueOf(square - number)).compareTo(BigDecimal.valueOf(0.0000000001)) < 0)
            return midvalue;
            //if the square root belongs to second half
        else if (square > number)
            return decimalSqrt(number, i, midvalue);
            //if the square root belongs to first half
        else
            return decimalSqrt(number, midvalue, j);
    }

    // same principle with decimalSqrt function using Newton's method
    public BigDecimal pow(BigDecimal base, BigDecimal exp) {
        BigDecimal temp;
        //covers prior to decimal point using the property x^y = x^(y/2)^2
        if (exp.compareTo(new BigDecimal(1)) >= 0) {
            temp = pow(base, exp.divide(BigDecimal.valueOf(2)));
            return temp.multiply(temp);
        }
        //now deal with the fractional part    
        else {
            BigDecimal low = BigDecimal.valueOf(0);
            BigDecimal high = BigDecimal.valueOf(1.0);
            BigDecimal sqr = sqrt(base);
            BigDecimal acc = sqr;
            BigDecimal mid = high.divide(BigDecimal.valueOf(2));

            // Accuracy is set to 10 decimal points
            while (abs(mid.min(exp)).compareTo(BigDecimal.valueOf(0.0000000001)) > 0) {
                sqr = sqrt(sqr);

                if (mid.compareTo(exp) <= 0) {
                    low = mid;
                    acc = acc.multiply(sqr);
                } else {
                    high = mid;
                    acc = acc.multiply(new BigDecimal(1).divide(sqr));
                }
                mid = (low.add(high)).divide(new BigDecimal(2));
            }
            return acc;
        }
    }

    // nth root of x = x^(1/n)
    public BigDecimal nthroot(BigDecimal n, BigDecimal x) {
        // if x is negative, returns error message
        if (x.compareTo(new BigDecimal(0)) < 0) {
            System.err.println("Negative!");
            return new BigDecimal(-1);
        }
        if (x.equals(new BigDecimal(0)))
            return new BigDecimal(0);
        BigDecimal x1 = x;
        BigDecimal x2 = x.divide(n);
        BigDecimal x3 = x1.min(x2);
        if (x3.compareTo(new BigDecimal(0)) < 0)
            x3 = x3.multiply(new BigDecimal(-1));
        while (x3.compareTo(new BigDecimal("0.0000000001")) > 0) {
            x1 = x2;
            x2 = ((n.min(new BigDecimal("1.0"))).multiply(x2).add(x.divide(pow(x2, n.min(new BigDecimal("1.0")))).divide(n)));
        }
        return x2;
    }

    public double logHelper(double x) {
        if (x <= 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        double result = 0;
        double term = (x - 1) / x;
        int denominator = 2;
        while (term != 0) {
            result -= term;
            term = Math.pow((x - 1) / x, denominator) / denominator;
            denominator++;
        }
        return result;
    }
    
    // Method to calculate sqrt of a number
	public static BigDecimal sqrt(BigDecimal x) {
		BigDecimal i = BigDecimal.ONE;
		while (true) {
			if (i.multiply(i).equals(x))
				return i;
			else if (i.multiply(i).compareTo(x) > 0)
				return decimalSqrt(x, i.subtract(BigDecimal.ONE), i);
			i = i.add(BigDecimal.ONE);
		}
	}

	// Method to calculate decimal sqrt of a number
	private static BigDecimal decimalSqrt(BigDecimal number, BigDecimal i, BigDecimal j) {
		BigDecimal midvalue = i.add(j).divide(BigDecimal.TWO);
		BigDecimal square = midvalue.multiply(midvalue);
		BigDecimal error = square.subtract(number).abs();
		if (square.equals(number) || error.compareTo(THRESHOLD) < 0)
			return midvalue;
		else if (square.compareTo(number) > 0)
			return decimalSqrt(number, i, midvalue);
		else
			return decimalSqrt(number, midvalue, j);
	}
   
}

