package com.tykj.cloud.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lukw
 * @email 13507615840@163.com
 * @create 2018-07-27 17:56
 **/
@SpringBootApplication
public class RabbitMqApplication {

    private static Logger logger = LoggerFactory.getLogger(RabbitMqApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RabbitMqApplication.class,args);
        logger.info("rabbitMq application start successfully=================");
    }
}
