package com.cloud.rabbitmq.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lukew
 * @eamil 13507615840@163.com
 * @create 2018-08-08 21:56
 **/
@RestController
public class RabbitMqController {

    @GetMapping("/msg")
    public String msg() {
        return "msg";
    }
}
