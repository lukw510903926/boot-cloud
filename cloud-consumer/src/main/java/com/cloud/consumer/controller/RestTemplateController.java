package com.cloud.consumer.controller;

import com.cloud.consumer.service.HystrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * restTemplate 调用
 *
 * @author lukew
 * @eamil 13507615840@163.com
 * @create 2018-08-03 16:44
 **/
@RestController
public class RestTemplateController {

    @Autowired
    private HystrixService hystrixService;

    @GetMapping("/rest/list")
    public Object list() {
        return this.hystrixService.list();
    }
}
