package com.softweavers.eternity;

import com.softweavers.eternity.Domain.ExpressionEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static com.softweavers.eternity.Domain.Parser.evaluateFunctions;

@SpringBootApplication
public class EternityApplication {
	public static ExpressionEvaluator engine = new ExpressionEvaluator();

	public static void main(String[] args) {
		final Logger LOGGER = LoggerFactory.getLogger(EternityApplication.class);
		SpringApplication.run(EternityApplication.class, args);

		LOGGER.info("Eternity is now running");
		try {
			Resource banner = new ClassPathResource("static/banner.txt");
			String bannerText = new String(banner.getInputStream().readAllBytes());
			System.out.println(bannerText);


			try {
				String expr = "sd(sd(1,2), ab^x(pow(sd(2,pow(6+3/2)))), pow(pow(pow(logbx(5),(2/4*8))), 2+4)) + 6";
				String evaluatedFunctionExpr = evaluateFunctions(expr);
				LOGGER.info("Simplified expression: " + evaluatedFunctionExpr);
				LOGGER.info("Result: " + engine.evaluate(evaluatedFunctionExpr));
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		} catch (IOException e) {
			LOGGER.error("Banner could not be loaded: " + e.getMessage());
		}
		LOGGER.info("Eternity initialization completed");
	}
}

