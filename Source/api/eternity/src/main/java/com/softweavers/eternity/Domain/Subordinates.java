package com.softweavers.eternity.Domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class Subordinates {

    // Method to calculate sqrt of a number
    public static BigDecimal sqrt(BigDecimal x) {
        BigDecimal i = BigDecimal.ONE;
        while (true) {
            if (i.multiply(i) == x)
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
        if (square == number || error.compareTo(THRESHOLD) < 0)
            return midvalue;
        else if (square.compareTo(number) > 0)
            return decimalSqrt(number, i, midvalue);
        else
            return decimalSqrt(number, midvalue, j);
    }

    public BigInteger abs(BigInteger a) {
        if (a >= 0)
            return a;
        else
            return -a;
    }

    public BigDecimal abs(BigDecimal a) {
        if (a >= 0)
            return a;
        else
            return -a;
    }

    public BigInteger factorial(BigInteger f) {
        if (f < 0) {
            Main.LOGGER.info("Error: Invalid input");
            return null;
        }
        int i, fct = 1;
        for (i = 1; i <= f; i++) {
            fct = fct * i;
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
        if (square == number || abs(square - number) < 0.0000000001)
            return midvalue;
            //if the square root belongs to second half
        else if (square > number)
            return decimalSqrt(number, i, midvalue);
            //if the square root belongs to first half
        else
            return decimalSqrt(number, midvalue, j);
    }

    // same principle with decimalSqrt function using Newton's method
    public double pow(double base, double exp) {
        double temp = 0;
        //covers prior to decimal point using the property x^y = x^(y/2)^2
        if (exp >= 1) {
            temp = pow(base, exp / 2);
            return temp * temp;
        }
        //now deal with the fractional part
        else {
            double low = 0;
            double high = 1.0;
            double sqr = sqrt(base);
            double acc = sqr;
            double mid = high / 2;

            // Accuracy is set to 10 decimal points
            while (abs(mid - exp) > 0.0000000001) {
                sqr = sqrt(sqr);

                if (mid <= exp) {
                    low = mid;
                    acc *= sqr;
                } else {
                    high = mid;
                    acc *= (1 / sqr);
                }
                mid = (low + high) / 2;
            }
            return acc;
        }
    }

    // nth root of x = x^(1/n)
    public BigDecimal nthroot(BigDecimal n, BigDecimal x) {
        // if x is negative, returns error message
        if (x < 0) {
            System.err.println("Negative!");
            return -1;
        }
        if (x == 0)
            return 0;
        BigDecimal x1 = x;
        BigDecimal x2 = x / n;
        BigDecimal x3 = x1 - x2;
        if (x3 < 0)
            x3 = x3 * -1;
        while (x3 > 0.0000000001) {
            x1 = x2;
            x2 = ((n - 1.0) * x2 + x / pow(x2, n - 1.0)) / n;
        }
        return x2;
    }

    public BigDecimal logHelper(BigDecimal x) {
        if (x.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        BigDecimal result = BigDecimal.ZERO;
        BigDecimal temp = (x.subtract(BigDecimal.ONE)).divide(x, MathContext.DECIMAL128);
        BigDecimal term = temp;
        int denominator = 2;
        while (term.compareTo(BigDecimal.ZERO) != 0) {
            result = result.subtract(term);
            term = BigDecimal.valueOf(pow(temp.doubleValue(), denominator)).divide(BigDecimal.valueOf(denominator), MathContext.DECIMAL128);
            denominator++;
        }
        return result;
    }

}
/*    
public static void main(String[] args)   
{  
double base = 0, exp=0;    
Scanner sc = new Scanner(System.in);  
System.out.print("Enter a number: ");   
base = sc.nextDouble();
exp = sc.nextDouble();    
double result = pow(base, exp);  
System.out.println(result);  
}   
*/

