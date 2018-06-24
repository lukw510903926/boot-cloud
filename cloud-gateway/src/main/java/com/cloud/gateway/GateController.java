package com.cloud.gateway;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GateController {

	@Autowired
	private GatewayProperties gatewayProperties;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/gate")
	public List<RouteDefinition> properties() {

		logger.info("router :{}", gatewayProperties.getRoutes());
		return gatewayProperties.getRoutes();
	}
}
