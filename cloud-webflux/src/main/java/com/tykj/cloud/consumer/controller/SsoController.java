package com.tykj.cloud.consumer.controller;

import com.tykj.cloud.security.autoconfigure.SsoClientProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lukw
 * @email 13507615840@163.com
 * @create 2018-07-17 10:28
 **/
@RestController
@RequestMapping("/sso")
public class SsoController {

    @Autowired
    private SsoClientProperties ssoClientProperties;

    @GetMapping("/client")
    public String client(){

        return ssoClientProperties.getClientId() + " : "+ ssoClientProperties.getClientKey();
    }
}
