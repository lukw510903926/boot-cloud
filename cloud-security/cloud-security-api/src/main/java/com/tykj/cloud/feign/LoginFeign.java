package com.tykj.cloud.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tykj.cloud.common.web.LoginUser;
import com.tykj.cloud.entity.SystemUser;

@FeignClient("${cloud.api.security.serviceId}")
public interface LoginFeign {

	@PostMapping("/login")
	LoginUser login(@RequestBody SystemUser systemUser);

	@GetMapping("/{clientId}/{clientKey}/{token}")
	LoginUser token(@PathVariable("clientId") String clientId, @PathVariable("clientKey") String clientKey,
			@PathVariable("token") String token);
}
