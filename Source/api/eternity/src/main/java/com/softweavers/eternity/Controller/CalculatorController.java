package com.softweavers.eternity.Controller;

import com.softweavers.eternity.Common.EquationParam;
import com.softweavers.eternity.Common.URI;
import com.softweavers.eternity.Service.CalculatorService;
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
     *
     * @param equationParam EquationParam object containing validated input equation and other params
     * @return calculated string value
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = URI.GET_CALCULATION_RESULT, method = RequestMethod.POST)
    ResponseEntity<?> calculate(@RequestBody EquationParam equationParam) {
        try {
            LOGGER.debug("CalculatorController: calculate -- Started");
            BigDecimal result = calculatorService.calculate(equationParam);

            LOGGER.debug("CalculatorController: calculate -- Success");
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            LOGGER.error("CalculatorController: calculate -- Error");
            return ResponseEntity.badRequest().body("FAILURE: " + e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = URI.TEST_CALCULATIONS, method = RequestMethod.GET)
    ResponseEntity<?> runTests() {
        try {
            LOGGER.debug("CalculatorController: runTests -- Started");
            Boolean result = calculatorService.runTests();

            LOGGER.debug("CalculatorController: runTests -- Success");
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            LOGGER.error("CalculatorController: calculate -- Error");
            return ResponseEntity.badRequest().body("FAILURE: " + e.getMessage());
        }
    }
}
