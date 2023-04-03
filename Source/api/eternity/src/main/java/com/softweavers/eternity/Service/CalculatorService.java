package com.softweavers.eternity.Service;

import com.softweavers.eternity.Common.EquationParam;
import com.softweavers.eternity.Controller.CalculatorController;
import com.softweavers.eternity.Domain.Parser;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.math.BigDecimal;
import java.math.MathContext;

@Service
public class CalculatorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorService.class);
    private static final ScriptEngineManager MANAGER = new ScriptEngineManager();
    private static final ScriptEngine ENGINE = MANAGER.getEngineByName("js");
    public BigDecimal calculate(EquationParam equation) throws ScriptException {

        int precision = equation.precision;
        String evaluatedFunctionExpr = Parser.evaluateFunctions(equation.value);

        Object evaluatedResult = ENGINE.eval(evaluatedFunctionExpr);
        BigDecimal result = new BigDecimal(evaluatedResult.toString()).round(new MathContext(precision));

        return result;
    }
}
