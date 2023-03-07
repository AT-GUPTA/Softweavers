package com.softweavers.eternity.Domain;
import java.util.ArrayList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Parser {
    private static final ScriptEngineManager manager = new ScriptEngineManager();
    private static final ScriptEngine engine = manager.getEngineByName("js");

    public static void main(String[] args) {
        try {
            String expr = "(5 + mad(5 , mad(3,5), 6)) + sinh(pow(sd(1,2,3), 2))";
            String evaluatedFunctionExpr = evaluateFunctions(expr);
            System.out.println(evaluatedFunctionExpr);
            System.out.println(engine.eval(evaluatedFunctionExpr));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static String evaluateFunctions(String expr) {
        System.out.println("expr: " + expr);
        expr = expr.replace(" ", "");
        String[] functions = { "mad", "sd", "ab^x", "arccos", "sinh", "gamma", "pow" };
        for (String func : functions) {
            while (expr.contains(func)) {
                System.out.println("func: " + func);
                int funcStart = expr.indexOf(func);
                int inputStart = funcStart + func.length() + 1;
                int inputEnd = indexOfClosingBracket(expr, inputStart);
                // get all inputs of the function, can be multiple seperated by commas or single
                // input
                String[] inputs = split(expr.substring(inputStart, inputEnd));
                System.out.println("inputs: " + expr.substring(inputStart, inputEnd));
                for (int i = 0; i < inputs.length; ++i) {
                    if (isComplexExpr(inputs[i])) {
                        // if an input of the function is an expression, recursively eval
                        // and replace expression with evaluated expression
                        System.out.println("complex expr: " + inputs[i]);
                        inputs[i] = evaluateFunctions(inputs[i]);
                    }
                }
                String res = "";
                switch (func) {
                    case "logbx" -> // res = mad(inputs)
                            res = "1";
                    case "sd" -> // res = sd(inputs)
                            res = "2";
                    case "ab^x" -> // res = abx(inputs)
                            res = "3";
                    case "arccos" -> // res = arccos(inputs[0])
                            res = "4";
                    case "sinh" -> // res = sinh(inputs[0])
                            res = "5";
                    case "gamma" -> // res = gamma(inputs[0])
                            res = "6";
                    case "pow" -> // res = pow(inputs[0], inputs[1])
                            res = "7";
                }
                expr = expr.substring(0, funcStart) + res + expr.substring(inputEnd + 1);
            }
        }
        return expr;
    }

    private static String[] split(String expr) {
        expr += ",";
        ArrayList<String> arr = new ArrayList<>();
        StringBuilder currentInput = new StringBuilder();
        boolean isInsideFunction = false;
        for (int i = 0; i < expr.length(); ++i) {
            if (expr.charAt(i) == '(')
                isInsideFunction = true;
            if (expr.charAt(i) == ')')
                isInsideFunction = false;
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

    private static int precedence(String symbol) {
        switch (symbol) {
            case "+":
            case "-":
                return 2;
            case "*":
            case "/":
                return 3;
            case "^":
                return 4;
            case "(":
            case ")":
                return 1;
            default:
                return 0;
        }
    }

    private static boolean isOperator(String symbol) {
        switch (symbol) {
            case "+":
            case "-":
            case "*":
            case "/":
            case "^":
            case "(":
            case ")":
                return true;
            default:
                return false;
        }
    }

    private static boolean isComplexExpr(String expr) {
        try {
            Float.parseFloat(expr);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }
}
