package com.softweavers.eternity.Service;

import com.softweavers.eternity.Domain.Parser;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CalculatorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorService.class);

    public BigDecimal calculate(JSONObject json) {
        String evaluatedFunctionExpr = "";
        try {
            String expr = json.optString("formula");
            LOGGER.info("Recieved: " + expr);
            Parser parse = new Parser();
            evaluatedFunctionExpr = parse.evaluateFunctions(expr);
            LOGGER.info("Result: " + evaluatedFunctionExpr);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return new BigDecimal(evaluatedFunctionExpr);
    }
}
