package com.example.sweta.onetomanymapping;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
class OnetomanymappingApplicationTests {

	public static Logger logger =LoggerFactory.getLogger(OnetomanymappingApplication.class);
	
	@Test
	public void contextLoads() {
		logger.info("Test case executing");
		assertEquals(true,true);
	}

}
