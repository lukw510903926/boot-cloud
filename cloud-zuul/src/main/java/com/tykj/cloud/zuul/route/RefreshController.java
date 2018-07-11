package com.tykj.cloud.zuul.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.web.bind.annotation.GetMapping;

//@RestController
public class RefreshController {

    @Autowired
    private RefreshRouteServiceImpl refreshRouteService;

    @Autowired
    ZuulHandlerMapping zuulHandlerMapping;

    @GetMapping("/refreshRoute")
    public String refresh() {

        refreshRouteService.refreshRoute();
        return "refresh success";
    }

    @GetMapping("/watchRoute")
    public Object watchNowRoute() {

        return zuulHandlerMapping.getHandlerMap();
    }
}
