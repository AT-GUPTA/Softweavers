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
}
