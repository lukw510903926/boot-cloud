package com.tykj.cloud.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class ConfigApplication {

	private static Logger logger = LoggerFactory.getLogger(ConfigApplication.class);

	public static void main(String[] args) {
		
		SpringApplication.run(ConfigApplication.class, args);
		logger.info(" config application start successfully--------------");
	}

}
