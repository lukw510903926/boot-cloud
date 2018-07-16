package com.tykj.cloud.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * springboot 监控
 *
 * @author lukew
 * @eamil 13507615840@163.com
 * @create 2018-07-14 22:31
 **/
@EnableAdminServer
@SpringBootApplication
public class AdminApplication {

    private static Logger logger = LoggerFactory.getLogger(AdminApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class,args);
        logger.info("admin application start successfully-----------");
    }
}
