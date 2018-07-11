package com.tykj.cloud.zuul.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

public class RouteConfig {

    @Autowired
    ZuulProperties zuulProperties;

    @Autowired
    ServerProperties server;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Bean
    public DBRouteLocator routeLocator() {

        DBRouteLocator routeLocator = new DBRouteLocator( server.getServlet().getContextPath(), this.zuulProperties);
        routeLocator.setJdbcTemplate(jdbcTemplate);
        return routeLocator;
    }
}
