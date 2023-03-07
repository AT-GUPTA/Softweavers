package com.softweavers.eternity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@SpringBootApplication
public class EternityApplication {


	public static void main(String[] args) {
		final Logger LOGGER = LoggerFactory.getLogger(EternityApplication.class);
		SpringApplication.run(EternityApplication.class, args);

		LOGGER.info("Eternity is now running");
		try {
			Resource banner = new ClassPathResource("static/banner.txt");
			String bannerText = new String(banner.getInputStream().readAllBytes());
			System.out.println(bannerText);
		} catch (IOException e) {
			LOGGER.error("Banner could not be loaded: " + e.getMessage());
		}
		LOGGER.info("Eternity initialization completed");
	}
}

