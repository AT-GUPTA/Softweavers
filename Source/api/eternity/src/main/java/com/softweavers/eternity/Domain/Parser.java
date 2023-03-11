package com.softweavers.eternity.Domain;

import java.util.ArrayList;
import java.util.Arrays;

import static com.softweavers.eternity.Domain.Functions.StandardDeviation.standardDeviation;


public class Parser {

    public static ExpressionEvaluator engine = new ExpressionEvaluator();

    public static String evaluateFunctions(String expr){
        System.out.println("expr: " + expr);
        expr = expr.replace(" ", "");
        String[] functions = { "logbx", "sd", "ab^x", "arccos", "sinh", "gamma", "pow" };
        for (String func : functions) {
            while (expr.contains(func)) {
                int funcStart = expr.indexOf(func);
                int inputStart = funcStart + func.length() + 1;
                int inputEnd = indexOfClosingBracket(expr, inputStart);
                // get all inputs of the function, can be multiple seperated by commas or single
                // input
                String[] inputs = split(expr.substring(inputStart, inputEnd));
                System.out.println("function: " + func + ", inputs: " + expr.substring(inputStart, inputEnd));
                for (int i = 0; i < inputs.length; ++i) {
                    if (isComplexExpr(inputs[i])) {
                        // if an input of the function is an expression, recursively eval
                        // and replace expression with evaluated expression
                        System.out.println("complex expr: " + inputs[i]);
                        inputs[i] = evaluateFunctions(inputs[i]);

                        // Solve any complex expressions with basic operations
                        inputs[i] = Double.toString(engine.evaluate(inputs[i]));
                    }
                }
                String res = "";
                switch (func) {
                    case "logbx": {
                        // res = mad(inputs)
                        res = "1";
                        break;
                    }
                    case "sd": {
//                        for (int i = 0; i < inputs.length; i++) {
//                            inputs[i] = engine.eval(inputs[i]).toString();
//                        }
                        res = standardDeviation(inputs);
                        break;
                    }
                    case "ab^x": {
                        // res = abx(inputs)
                        res = "3";
                        break;
                    }
                    case "arccos": {
                        // res = arccos(inputs[0])
                        res = "4";
                        break;
                    }
                    case "sinh": {
                        // res = sinh(inputs[0])
                        res = "5";
                        break;
                    }
                    case "gamma": {
                        // res = gamma(inputs[0])
                        res = "6";
                        break;
                    }
                    case "pow": {
                        // res = pow(inputs[0], inputs[1])
                        res = "7";
                        break;
                    }
                }
                expr = expr.substring(0, funcStart) + res + expr.substring(inputEnd + 1);
                System.out.println(func + "(" + Arrays.toString(inputs) + ") = " + res);
            }
        }
        return expr;
    }

    private static String[] split(String expr) {
        expr += ",";
        ArrayList<String> arr = new ArrayList<>();
        StringBuilder currentInput = new StringBuilder();
        boolean isInsideFunction = false;
        int openCount = 0;
        for (int i = 0; i < expr.length(); ++i) {
            if (expr.charAt(i) == '(') {
                openCount++;
                isInsideFunction = true;
            }
            if (expr.charAt(i) == ')') {
                openCount--;
                if (openCount == 0)
                    isInsideFunction = false;
            }
            if ((expr.charAt(i) == ',' && !isInsideFunction)) {
                arr.add(currentInput.toString());
                currentInput = new StringBuilder();
            } else {
                currentInput.append(expr.charAt(i));
            }
        }
        String[] result = new String[arr.size()];
        for (int i = 0; i < arr.size(); ++i) {
            result[i] = arr.get(i);
        }
        return result;
    }

    private static int indexOfClosingBracket(String expr, int indexOfStartingBracket) {
        int sum = 1;
        for (int i = indexOfStartingBracket + 1; i < expr.length(); ++i) {
            if (expr.charAt(i) == '(') {
                sum++;
            }
            if (expr.charAt(i) == ')') {
                sum--;
            }
            if (sum == 0) {
                return i;
            }
        }
        return -1;
    }

    private static boolean isComplexExpr(String expr) {
        try {
            Float.parseFloat(expr);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

//    private static String eval(){
//
//    }
}
