package com.softweavers.eternity.Service;

import com.softweavers.eternity.Common.EquationParam;
import com.softweavers.eternity.Domain.FunctionParser;
import com.softweavers.eternity.Domain.FunctionTest;
import com.softweavers.eternity.Domain.ParserHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CalculatorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorService.class);
    private final ParserHandler parser = new FunctionParser();

    private final FunctionTest testClass = new FunctionTest();

    public BigDecimal calculate(EquationParam equationParam) {
        String evaluatedFunctionExpr;
        BigDecimal result;
        try {
            String expr = equationParam.formula;
            LOGGER.info("Received: " + expr);

            evaluatedFunctionExpr = parser.evaluateFunctions(expr);
            result = new BigDecimal(evaluatedFunctionExpr);
            LOGGER.info("Result: " + evaluatedFunctionExpr);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }

        return result.setScale(equationParam.precision, RoundingMode.HALF_UP);
    }

    public Boolean runTests() {
        try {
            testClass.executeTests();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
