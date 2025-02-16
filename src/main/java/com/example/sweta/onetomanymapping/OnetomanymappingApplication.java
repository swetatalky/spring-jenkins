package com.example.sweta.onetomanymapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class OnetomanymappingApplication {
	public static Logger logger =LoggerFactory.getLogger(OnetomanymappingApplication.class);
	@PostConstruct
	public void init()
	{
		logger.info("Application Started...");
	}

	public static void main(String[] args) {
		logger.info("Main Started...");
		SpringApplication.run(OnetomanymappingApplication.class, args);
	}

}
