package com.tykj.cloud.security.controller;

import com.tykj.cloud.common.web.RestResult;
import com.tykj.cloud.security.autoconfigure.SsoClientProperties;
import com.tykj.cloud.security.entity.SystemUser;
import com.tykj.cloud.security.feign.LoginFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.tykj.cloud.common.web.LoginUser;

/**
 * @Description:
 * @author lukew
 * @email 13507615840@163.com
 * @date 2018年7月16日 下午8:20:33
 */
@RestController("/login")
public class LoginController implements LoginFeign {

	@Autowired
	private SsoClientProperties ssoClientProperties;

	@Override
	public RestResult<LoginUser> login(SystemUser systemUser) {
		return RestResult.success(new LoginUser());
	}

	@Override
	public RestResult<LoginUser> token(String clientId, String clientKey, String token) {

		logger.info("ssoClientProperties: {}",ssoClientProperties);
		return RestResult.success(new LoginUser());
	}
}
