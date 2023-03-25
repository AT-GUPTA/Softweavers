package com.softweavers.eternity.Service;

import com.softweavers.eternity.Domain.FunctionParser;
import com.softweavers.eternity.Domain.ParserHandler;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CalculatorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorService.class);
    private final ParserHandler parser = new FunctionParser();

    public BigDecimal calculate(JSONObject json) {
        String evaluatedFunctionExpr = "";
        try {
            String expr = json.optString("formula");
            LOGGER.info("Recieved: " + expr);

            evaluatedFunctionExpr = parser.evaluateFunctions(expr);

            LOGGER.info("Result: " + evaluatedFunctionExpr);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
        return new BigDecimal(evaluatedFunctionExpr);
    }
}
