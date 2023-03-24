package com.softweavers.eternity.Domain;

import java.math.BigDecimal;
import com.softweavers.eternity.Domain.FunctionsImpl;

import static com.softweavers.eternity.Domain.FunctionsImpl.PRECISION;

public class ExpressionEvaluator {
    private int index = 0;
    private String expression;

    public BigDecimal evaluate(String expression) {
        this.expression = expression;
        index = 0;
        return evaluateExpression();
    }

    private BigDecimal evaluateExpression() {
        // Recursively get the left value
        BigDecimal result = evaluateTerm();

        while (index < expression.length()) {
            char operator = expression.charAt(index);

            if (operator == '+' || operator == '-') {
                index++;
                // Get the right value
                BigDecimal term = evaluateTerm();

                if (operator == '+') {
                    result = result.add(term);
                } else {
                    result = result.subtract(term);
                }
            } else {
                break;
            }
        }

        return result;
    }

    private BigDecimal evaluateTerm() {
        // Recursively get the left value
        BigDecimal result = evaluateParentheses();

        while (index < expression.length()) {
            char operator = expression.charAt(index);

            if (operator == '*' || operator == '/') {
                index++;
                // Recursively get the right value
                BigDecimal factor = evaluateParentheses();

                if (operator == '*') {
                    result = result.multiply(factor);
                } else {
                    result = result.divide(factor, PRECISION);
                }
            } else {
                break;
            }
        }

        return result;
    }

    private BigDecimal evaluateParentheses() {
        BigDecimal result;

        char c = expression.charAt(index);

        if (c == '(') {
            index++;
            // Recursive call for additional parentheses
            result = evaluateExpression();
            index++; // consume closing parentheses
        } else if (Character.isDigit(c) || c == '.') {
            int start = index;
            while (Character.isDigit(c) || c == '.') {
                index++;
                if (index >= expression.length()) {
                    break;
                }
                c = expression.charAt(index);
            }
            result = new BigDecimal(expression.substring(start, index));
        } else if (c == '-') {
            index++;
            // handle negative number
            result = evaluateParentheses().negate();
        } else {
            throw new IllegalArgumentException("Invalid character: " + c);
        }

        return result;
    }
}