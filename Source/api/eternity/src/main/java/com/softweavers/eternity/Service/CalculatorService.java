package com.softweavers.eternity.Service;

import com.softweavers.eternity.Controller.CalculatorController;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CalculatorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorService.class);

    public BigDecimal calculate(JSONObject json) {
        //int precision = json.getInt("precision");
        return null;
    }
}
