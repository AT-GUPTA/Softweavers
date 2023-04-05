package com.softweavers.eternity.Service;

import com.softweavers.eternity.Common.EquationParam;
import com.softweavers.eternity.Domain.FunctionParser;
import com.softweavers.eternity.Domain.ParserHandler;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;

@Service
public class CalculatorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorService.class);
    private final ParserHandler parser = new FunctionParser();

    public BigDecimal calculate(EquationParam equationParam) {
        String evaluatedFunctionExpr = "";
        try {
            String expr = equationParam.formula;
            LOGGER.info("Received: " + expr);

            evaluatedFunctionExpr = parser.evaluateFunctions(expr);

            LOGGER.info("Result: " + evaluatedFunctionExpr);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
        return new BigDecimal(evaluatedFunctionExpr).round(new MathContext(equationParam.precision));
    }
}
