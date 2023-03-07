package com.softweavers.eternity.Controller;

import com.softweavers.eternity.Common.URI;
import com.softweavers.eternity.Service.CalculatorService;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class CalculatorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorController.class);

    @Autowired
    private CalculatorService calculatorService;

    /**
     * Method to calculate the value of input equation
     * @param equationJson json object containing validated input equation and other params
     * @return calculated string value
     */
    @CrossOrigin
    @RequestMapping(value = URI.GET_CALCULATION_RESULT, method = RequestMethod.GET)
    ResponseEntity<?> calculate(@RequestBody String equationJson) {
        try {
            LOGGER.debug("CalculatorController: calculate -- Started");
            JSONObject json = new JSONObject(equationJson);
            BigDecimal result = calculatorService.calculate(json);
            LOGGER.debug("CalculatorController: calculate -- Success");
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            LOGGER.error("CalculatorController: calculate -- Error");
            return ResponseEntity.badRequest().body("FAILURE");
        }
    }
}
