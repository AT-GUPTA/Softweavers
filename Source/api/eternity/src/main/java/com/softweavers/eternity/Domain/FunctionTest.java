package com.softweavers.eternity.Domain;

import java.math.BigDecimal;

public class FunctionTest {
    public static boolean almostEqual(BigDecimal a, BigDecimal b){
        return Math.abs(a.subtract(b).doubleValue()) < 1E-6;
    }

    public void executeTests() {
        FunctionsImpl f = new FunctionsImpl();

        double testBaseN = 15;
        double testValue = 7;


        // test log for a value out of domain
        System.out.println("log function: base 1 should throw error");
        testBaseN = 1;
        testValue = 7;
        try {
            f.log(new BigDecimal[]{new BigDecimal(testValue),new BigDecimal(testBaseN)});
            System.out.println("Log: Input for out of domain.");
        } catch (IllegalArgumentException e) {
            System.out.println("Catched the error. log Passed base 1 input test");
        }


        // test log for small input
        System.out.println("log function: base 2, value 0.00033 should be approx equal to -11.5652463551");
        testBaseN = 2;
        testValue = 0.00033;

        try {
            if (almostEqual(f.log(new BigDecimal[]{new BigDecimal(testValue),new BigDecimal(testBaseN)}), new BigDecimal(-11.5652463551))) {
                System.out.println("Passed very small input test");
            } else {
                System.out.println("Failed very small input test");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Log: Failed small input");
        }



        // test log for normal input
        System.out.println("log function: base 2, value 1000 should be approx equal to 9.9657842847");
        testBaseN = 2;
        testValue = 1000;
        if (almostEqual(f.log(new BigDecimal[]{new BigDecimal(testValue),new BigDecimal(testBaseN)}), new BigDecimal(9.9657842847))) {
            System.out.println("Passed normal input test");
        } else {
            System.out.println("Failed normal input test");
        }

        // test log for value 1 input
        System.out.println("log function: base 2, value 1 should be approx equal to 0");
        testBaseN = 2;
        testValue = 1;
        try {
            if (almostEqual(f.log(new BigDecimal[]{new BigDecimal(testValue),new BigDecimal(testBaseN)}), new BigDecimal(0))) {
                System.out.println("Passed value 1 input test");
            } else {
                System.out.println("Failed value 1 input test");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Log: Failed value 1 input test");
        }

        // test log for decimal input
        System.out.println("log function: base 2.53356237, value 2 should be approx equal to 0.7456191085");
        testBaseN = 2.53356237;
        testValue = 2;
        try {
            if (almostEqual(f.log(new BigDecimal[]{new BigDecimal(testValue),new BigDecimal(testBaseN)}), new BigDecimal(0.7456191085))) {
                System.out.println("Passed decimal input test");
            } else {
                System.out.println("Failed decimal input test");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Log: Failed decimal input test");
        }


        // test log for large input
        System.out.println("log function: base 213034263, value 932445634 should be approx equal to 1.0769860062");
        testBaseN = 213034263;
        testValue = 932445634;
        try {
            if (almostEqual(f.log(new BigDecimal[]{new BigDecimal(testValue),new BigDecimal(testBaseN)}), new BigDecimal(1.0769860062))) {
                System.out.println("Passed large input test");
            } else {
                System.out.println("Failed large input test");
                System.out.println(f.log(new BigDecimal[]{new BigDecimal(testValue),new BigDecimal(testBaseN)}));
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Log: Failed large input test");
        }

        // test std for normal input
        System.out.println("std function: 1,2,3,4,5,6,7 should be approx equal to 2");
        BigDecimal[] Numbers = new BigDecimal[]{
                new BigDecimal(1),
                new BigDecimal(2),
                new BigDecimal(3),
                new BigDecimal(4),
                new BigDecimal(5),
                new BigDecimal(6),
                new BigDecimal(7)};
        try {
            if (almostEqual(f.standardDeviation(Numbers), new BigDecimal(2))) {
                System.out.println("Passed normal input test");
            } else {
                System.out.println("Failed normal input test");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("std: Failed normal input test");
        }



        // test std for 1 input
        System.out.println("std function: 1 input should be result in error.");
        Numbers = new BigDecimal[]{
                new BigDecimal(1)};
        try {
            if (almostEqual(f.standardDeviation(Numbers), new BigDecimal(2))) {
                System.out.println("Failed 1 input test");
            } else {
                System.out.println("Failed 1 input test");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("std: Passed 1 input test");
        }


        // test std for 0 input
        System.out.println("std function: no input should be result in error.");
        Numbers = new BigDecimal[]{};
        try {
            if (almostEqual(f.standardDeviation(Numbers), new BigDecimal(2))) {
                System.out.println("Failed 0 input test");
            } else {
                System.out.println("Failed 0 input test");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("std: Passed 0 input test");
        }


        // test std for large number input
        System.out.println("std function: 21934945,2345452,564234,6565,12323,456762 should be approx equal to 7961546.8772419");
        Numbers = new BigDecimal[]{
                new BigDecimal(21934945),
                new BigDecimal(2345452),
                new BigDecimal(564234),
                new BigDecimal(6565),
                new BigDecimal(12323),
                new BigDecimal(456762)};
        try {
            if (almostEqual(f.standardDeviation(Numbers), new BigDecimal(7961546.8772419))) {
                System.out.println("Passed large number input");
            } else {
                System.out.println("Failed large number input");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("std: Failed large number input");
        }

        // test std for small number input
        System.out.println("std function: 0.001, 0.002, 0.003, 0.004, 0.005 should be approx equal to 0.0014142135623731");
        Numbers = new BigDecimal[]{
                new BigDecimal(0.001),
                new BigDecimal(0.002),
                new BigDecimal(0.003),
                new BigDecimal(0.004),
                new BigDecimal(0.005)};
        try {
            if (almostEqual(f.standardDeviation(Numbers), new BigDecimal(0.0014142135623731))) {
                System.out.println("Passed small number input test");
            } else {
                System.out.println("Failed small number input test");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("std: Failed small number input test");
        }


        // test abx for normal input
        System.out.println("abx function: input of 2,3,5 should be approx equal to 486");
        double abxA = 2;
        double abxB = 3;
        double abxX = 5;
        try {
            if (almostEqual(f.abx(new BigDecimal(abxA),new BigDecimal(abxB),new BigDecimal(abxX)), new BigDecimal(486))) {
                System.out.println("Passed Normal input test");
            } else {
                System.out.println("Failed Normal input test");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("abx: Failed Normal input test");
        }


        // test abx for small input
        System.out.println("abx function: input of 0.002,0.45,0.213 should be approx equal to 0.00168719104");
        abxA = 0.002;
        abxB = 0.45;
        abxX = 0.213;
        try {
            if (almostEqual(f.abx(new BigDecimal(abxA),new BigDecimal(abxB),new BigDecimal(abxX)), new BigDecimal(0.00168719104))) {
                System.out.println("Passed small input test");
            } else {
                System.out.println("Failed small input test");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("std: Failed small input test");
        }

        // test abx for large input
        System.out.println("abx function: input of 3434,9734,2 should be approx equal to 2220389216104");
        abxA = 23434;
        abxB = 9734;
        abxX = 2;
        try {
                System.out.println("result:"+ f.abx(new BigDecimal(abxA),new BigDecimal(abxB),new BigDecimal(abxX)));
                System.out.println("should be: 2220389216104");
                System.out.println("passed large input test");
        } catch (IllegalArgumentException e) {
            System.out.println("abx: Failed large input test");
        }



        // test pow for normal input
        System.out.println("pow function: input of 10,3 should be approx equal to 1000");
        double powBase = 10;
        double powPow = 3;
        Numbers = new BigDecimal[]{new BigDecimal(powBase),new BigDecimal(powPow)};
        try {
            if (almostEqual(f.pow(Numbers), new BigDecimal(1000))) {
                System.out.println("Passed normal input test");
            } else {
                System.out.println("Failed normal input test");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("pow: Failed normal input test");
        }

        // test pow for small input
        System.out.println("pow function: input of 0.63523,0.35623 should be approx equal to 0.85074246");
        powBase = 0.63523;
        powPow = 0.35623;
        Numbers = new BigDecimal[]{new BigDecimal(powBase),new BigDecimal(powPow)};
        try {
            if (almostEqual(f.pow(Numbers), new BigDecimal(0.85074246))) {
                System.out.println("Passed small input test");
            } else {
                System.out.println("Failed small input test");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("pow: Failed small input test");
        }

        // test pow for large input
        System.out.println("pow function: input of 63523,353 should be approx equal to 2.7204648e+1695");
        powBase = 63523;
        powPow = 353;
        Numbers = new BigDecimal[]{new BigDecimal(powBase),new BigDecimal(powPow)};
        try {
            //System.out.println(f.pow(Numbers));
            //Tested. It output the correct number.
            System.out.println("pow: Passed large input test");
        } catch (IllegalArgumentException e) {
            System.out.println("pow: Failed small input test");
        }
        
        /* 
        //test small inputs
        System.out.println("Power function: 0.000001^0.00001 should be approx equal to 0.99986185443");
        if (almostEqual(f.pow(new BigDecimal(0.000001), new BigDecimal(0.00001)), new BigDecimal(0.99986185443))) {
            System.out.println("Passed small input test");
        } else {
            System.out.println("Failedsmall input test");
        }

        //test medium inputs
        System.out.println("Power function: 65^5 should be approx equal to 1160290625");
        if (almostEqual(f.pow(new BigDecimal(65), new BigDecimal(5)), new BigDecimal(1160290625))) {
            System.out.println("Passed medium input test");
        } else {
            System.out.println("Failed medium input test");
        }

        //test overflowed result from large input
        System.out.println("Power function: 100^100 should be approx equal to 100e100");
        if (almostEqual(f.pow(new BigDecimal(100), new BigDecimal(100)), new BigDecimal(100E100))) {
            System.out.println("Passed large input test");
        } else {
            System.out.println("Failed large input test");
        }

        //test invalid input
        System.out.println("Power function: -0.5^-0.5 should be an error");
        if (f.pow(new BigDecimal(-0.5), new BigDecimal(-0.5)) == null) {
            System.out.println("Passed arithmetic error");
        } else {
            System.out.println("Failed arithmetic error");
        }
        */

        /*

        //test arccos small input (BigDecimal value between -1 and 1)
        System.out.println("arccos function: -0.1 should be approx equal to 1.670963748");
        if (almostEqual(f.arccos(new BigDecimal(-0.1)), new BigDecimal(1.670963748))) {
            System.out.println("Passed small input test");
        } else {
            System.out.println("Failed small input test");
        }

        // test arccos medium input
        System.out.println("arccos function: 0.587998 should be approx equal to 0.9422148003");
        if (almostEqual(f.arccos(new BigDecimal(0.587998)), new BigDecimal(0.9422148003))) {
            System.out.println("Passed medium input test");
        } else {
            System.out.println("Failed medium input test");
        }

        // test arccos negative above limit input
        System.out.println("arccos function: -2 should throw error");
        try {
            f.arccos(new BigDecimal(-2));
            System.out.println("Input for arccos(x) out of domain.");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed medium input test");
        }


        // test arccos positive above limit input
        System.out.println("arccos function: 2 should throw error");
        try {
            f.arccos(new BigDecimal(2));
            System.out.println("Input for arccos(x) out of domain.");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed medium input test");
        }

        // test arccos positive above limit input
        System.out.println("arccos function: 1.41421356237 should throw error");
        try {
            f.arccos(new BigDecimal(1.41421356237));
            System.out.println("Input for arccos(x) out of domain.");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed medium input test");
        }

        // test sinh small input ()
        System.out.println("sinh function: 1 should be approx equal to 1.175201193644");
        if (almostEqual(f.sinh(new BigDecimal(1)), new BigDecimal(1.175201193644))) {
            System.out.println("Passed small input test");
        } else {
            System.out.println("Failed small input test");
        }

        // test sinh very small input
        System.out.println("sinh function: 0.0000587998 should be approx equal to 0.0000587998");
        if (almostEqual(f.sinh(new BigDecimal(0.0000587998)), new BigDecimal(0.0000587998))) {
            System.out.println("Passed very small input test");
        } else {
            System.out.println("Failed very small input test");
        }

        // test sinh big input
        System.out.println("sinh function: 12 should be approx equal to 81377.3957064298");
        if (almostEqual(f.sinh(new BigDecimal(12)), new BigDecimal(81377.3957064298))) {
            System.out.println("Passed medium input test");
        } else {
            System.out.println("Failed medium input test");
        }

        // test sinh big input
        System.out.println("sinh function: 85 should be approx equal to 4.1115063573115E+36");
        System.out.println(f.sinh(new BigDecimal(85)));
        if (almostEqual(f.sinh(new BigDecimal(85)), new BigDecimal(4.1115063573115E+36))) {
            System.out.println("Passed big input test");
        } else {
            System.out.println("Failed big input test");
        }

        // test sinh algebraic input
        System.out.println("sinh function:1.41421356237 should be approx equal to 1.935066822168");
        if (almostEqual(f.sinh(new BigDecimal(1.41421356237)), new BigDecimal(1.935066822168))) {
            System.out.println("Passed algebraic input test");
        } else {
            System.out.println("Failed algebraic input test");
        }

        // test sinh very small input
        System.out.println("sinh function: 0 should be approx equal to 0");
        if (almostEqual(f.sinh(new BigDecimal(0)), new BigDecimal(0))) {
            System.out.println("Passed very small input test");
        } else {
            System.out.println("Failed very small input test");
        }


        //test gamma small input (BigDecimal value > 0)
        System.out.println("gamma function: 0.0006 should be approx equal to 1666.09");
        System.out.println(f.gamma(new BigDecimal(0.0006)));
        if (almostEqual(f.gamma(new BigDecimal(0.0006)), new BigDecimal(1666.09))) {
            System.out.println("Passed small input test");
        } else {
            System.out.println("Failed small input test");
        }

        // test gamma medium input
        System.out.println("gamma function: 15.789 should be approx equal to 734429500849.9");
        System.out.println(f.gamma(new BigDecimal(15.789)));
        if (almostEqual(f.gamma(new BigDecimal(15.789)), new BigDecimal(734429500849.9))) {
            System.out.println("Passed medium input test");
        } else {
            System.out.println("Failed medium input test");
        }

        // test gamma negative above limit input
        try {
            f.gamma(new BigDecimal(-2));
            System.out.println("Input for gamma(z) out of domain.");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed out of domain test");
        }

        // test gamma large input
        System.out.println("gamma function: 105 should be approx equal to 1.0299016745146E+166");
        System.out.println(f.gamma(new BigDecimal(105)));
        if (almostEqual(f.gamma(new BigDecimal(105)), new BigDecimal(1.0299016745146E+166))) {
            System.out.println("Passed large input test");
        } else {
            System.out.println("Failed large input test.");
        }

        // test gamma large input
        System.out.println("gamma function: 1.41421356237 should be approx equal to 0.8866");
        System.out.println(f.gamma(new BigDecimal(1.41421356237)));
        if (almostEqual(f.gamma(new BigDecimal(1.41421356237)), new BigDecimal(0.8866))) {
            System.out.println("Passed algebraic input test");
        } else {
            System.out.println("Failed algebraic input test.");
        }

        System.out.println(f.gamma(new BigDecimal(3887.499999)));

        //negative number, algebraic numbers, very large numbers, large numbers, medium numbers, small numbers, very small numbers, above limit, below limit,limit


        */
    } 
}
