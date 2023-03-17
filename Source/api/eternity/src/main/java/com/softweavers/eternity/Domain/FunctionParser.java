package com.softweavers.eternity.Domain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;

import static com.softweavers.eternity.Domain.Functions.StandardDeviation.standardDeviation;

public class FunctionParser {

    String[] functions = { "logbx", "sd", "ab^x", "arccos", "sinh", "gamma", "pow" };
    private static final Logger LOGGER = LoggerFactory.getLogger(FunctionParser.class);
    private static final ExpressionEvaluator engine = new ExpressionEvaluator();
    public static final MathContext mc = new MathContext(20, RoundingMode.HALF_EVEN);


    public String evaluateFunctions(String expr){

        LOGGER.debug("expr: " + expr);

        expr = expr.replace(" ", "");

        for (String func : functions) {
            while (expr.contains(func)) {
                int funcStart = expr.indexOf(func);
                int inputStart = funcStart + func.length() + 1;
                int inputEnd = indexOfClosingBracket(expr, inputStart);

                // get all inputs of the function, can be multiple seperated by commas or single input
                String[] inputs = split(expr.substring(inputStart, inputEnd));

                LOGGER.debug("function: " + func + ", inputs: " + expr.substring(inputStart, inputEnd));

                for (int i = 0; i < inputs.length; ++i) {
                    if (isComplexExpr(inputs[i])) {
                        // if an input of the function is an expression, recursively eval
                        // and replace expression with evaluated expression
                        LOGGER.debug("complex expr: " + inputs[i]);

                        inputs[i] = evaluateFunctions(inputs[i]);

                        // Solve any complex expressions with basic operations
                        inputs[i] = engine.evaluate(inputs[i]).toString();
                    }
                }

                String res = "";
                switch (func) {
                    case "logbx" -> res = "1";
                    case "sd" -> res = standardDeviation(inputs);
                    case "ab^x" -> res = "3";
                    case "arccos" -> res = "4";
                    case "sinh" -> res = "5";
                    case "gamma" -> res = "6";
                    case "pow" -> res = "7";

                }
                expr = expr.substring(0, funcStart) + res + expr.substring(inputEnd + 1);


                LOGGER.debug(func + "(" + Arrays.toString(inputs) + ") = " + res);
            }
        }

        return engine.evaluate(expr).toString();
    }
    static String[] split(String expr) {
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

    static int indexOfClosingBracket(String expr, int indexOfStartingBracket) {
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

    static boolean isComplexExpr(String expr) {
        try {
            Float.parseFloat(expr);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }
}
