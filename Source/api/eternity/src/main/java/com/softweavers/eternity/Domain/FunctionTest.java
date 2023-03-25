package com.softweavers.eternity.Domain;

import java.math.BigDecimal;

public class FunctionTest {
    public static boolean almostEqual(BigDecimal a, BigDecimal b){
        return Math.abs(a.subtract(b).doubleValue()) < 1E-6;
    }

    public void executeTests() {
        FunctionsImpl f = new FunctionsImpl();
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
        // test arccos small input
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
        */

         
        // test sinh small input
        System.out.println("arccos function: 1 should be approx equal to 1.175201193644");
        if (almostEqual(f.sinh(new BigDecimal(1)), new BigDecimal(1.175201193644))) {
            System.out.println("Passed small input test");
        } else {
            System.out.println("Failed small input test");
        }

        // test sinh very small input
        System.out.println("arccos function: 0.0000587998 should be approx equal to 0.0000587998");
        if (almostEqual(f.sinh(new BigDecimal(0.0000587998)), new BigDecimal(0.0000587998))) {
            System.out.println("Passed very small input test");
        } else {
            System.out.println("Failed very small input test");
        }

        // test sinh big input
        System.out.println("arccos function: 85 should be approx equal to 81377.3957064298");
        if (almostEqual(f.sinh(new BigDecimal(12)), new BigDecimal(81377.3957064298))) {
            System.out.println("Passed medium input test");
        } else {
            System.out.println("Failed medium input test");
        }

        // test sinh big input
        System.out.println("arccos function: 85 should be approx equal to 4.1115063573115E+36");
        if (almostEqual(f.sinh(new BigDecimal(85)), new BigDecimal(4.1115063573115E+36))) {
            System.out.println("Passed big input test");
        } else {
            System.out.println("Failed big input test");
        }
        

        System.out.println(f.gamma(new BigDecimal(3887.499999)));



    } 
}
