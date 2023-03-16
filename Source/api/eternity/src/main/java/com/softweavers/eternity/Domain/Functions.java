package com.softweavers.eternity.Domain;

public class Functions {
    private final Subordinates subordinates = new Subordinates();

    
     /*
    taylor series for arccos = pi/2 - taylor series for sin
    for loop will calculate taylor series for sin
    trial and error to find right number of n
    final result will be pi/2 - result from for loop
    return result converted to big decimal
     */
    public static BigDecimal arccos(double x) {
        if (x > 1 || x < -1)
            throw new IllegalArgumentException("Input for arccos(x) out of domain.");
        else if (x == 1)
            return (BigDecimal.valueOf(0.0));
        else if (x == -1)
            return (BigDecimal.valueOf(Math.PI));
        else {
            double loop_result = 0;
            double fraction1, fraction2.
            int end = 30;
            for (int n = 0; n <= end; n++) {
                fraction1 = factorial(2 * n) / (pow(2, 2 * n) * pow(factorial(n), 2));
                fraction2 = (pow(x, ((2 * n) + 1))) / ((2 * n) + 1);
                loop_result = loop_result + (fraction1 * fraction2);
            }
            BigDecimal result = BigDecimal.valueOf((Math.PI / 2) - loop_result);
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

}
