package com.softweavers.eternity.Domain;

public class ExpressionEvaluator {

    private int index = 0;
    private String expression;

    public double evaluate(String expression) {
        this.expression = expression;
        index = 0;
        return evaluateExpression();
    }

    private double evaluateExpression() {
        double result = evaluateTerm();

        while (index < expression.length()) {
            char operator = expression.charAt(index);

            if (operator == '+' || operator == '-') {
                index++;
                double term = evaluateTerm();

                if (operator == '+') {
                    result += term;
                } else {
                    result -= term;
                }
            } else {
                break;
            }
        }

        return result;
    }

    private double evaluateTerm() {
        double result = evaluateFactor();

        while (index < expression.length()) {
            char operator = expression.charAt(index);

            if (operator == '*' || operator == '/') {
                index++;
                double factor = evaluateFactor();

                if (operator == '*') {
                    result *= factor;
                } else {
                    result /= factor;
                }
            } else {
                break;
            }
        }

        return result;
    }

    private double evaluateFactor() {
        double result;

        char c = expression.charAt(index);

        if (c == '(') {
            index++;
            result = evaluateExpression();
            index++; // consume closing parenthesis
        } else {
            int start = index;
            while (Character.isDigit(c) || c == '.') {
                index++;
                if (index >= expression.length()) {
                    break;
                }
                c = expression.charAt(index);
            }
            result = Double.parseDouble(expression.substring(start, index));
        }

        return result;
    }
}