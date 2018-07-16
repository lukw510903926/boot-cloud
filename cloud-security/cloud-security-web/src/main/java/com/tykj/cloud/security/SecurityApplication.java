package com.tykj.cloud.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 认证模块
 *
 * @author lukew
 * @eamil 13507615840@163.com
 * @create 2018-07-16 19:47
 **/
@SpringBootApplication
public class SecurityApplication {

    private static  Logger logger = LoggerFactory.getLogger(SecurityApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class,args);
        logger.info("SecurityApplication start successfully------");
    }
}
