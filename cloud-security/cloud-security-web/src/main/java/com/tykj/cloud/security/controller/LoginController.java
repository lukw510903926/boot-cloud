package com.tykj.cloud.security.controller;

import org.springframework.web.bind.annotation.RestController;

import com.tykj.cloud.common.web.LoginUser;
import com.tykj.cloud.entity.SystemUser;
import com.tykj.cloud.feign.LoginFeign;

/**
 * @Description:
 * @author lukew
 * @email 13507615840@163.com
 * @date 2018年7月16日 下午8:20:33
 */
@RestController("/login")
public class LoginController implements LoginFeign{

	@Override
	public LoginUser login(SystemUser systemUser) {
		return new LoginUser();
	}

	@Override
	public LoginUser token(String clientId, String clientKey, String token) {
		return new LoginUser();
	}
}
