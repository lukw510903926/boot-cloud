package com.cloud.security.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.common.web.LoginUser;
import com.cloud.common.web.RestResult;
import com.cloud.security.autoconfigure.SsoClientProperties;
import com.cloud.security.entity.SystemUser;
import com.cloud.security.feign.LoginFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lukew
 * @Description:
 * @email 13507615840@163.com
 * @date 2018年7月16日 下午8:20:33
 */
@RestController
@RequestMapping("/login")
public class LoginController implements LoginFeign {

    @Autowired
    private SsoClientProperties ssoClientProperties;

    @Override
    public RestResult<LoginUser> login(@RequestBody SystemUser systemUser) {
        LoginUser loginUser = new LoginUser();
        loginUser.setName(systemUser.getName());
        loginUser.setId(systemUser.getId());
        logger.info("login : {}", JSONObject.toJSONString(loginUser));
        return RestResult.success(new LoginUser());
    }

    @Override
    @GetMapping("/{clientId}/{clientKey}/{token}")
    public RestResult<LoginUser> token(@PathVariable("clientId") String clientId, @PathVariable("clientKey") String clientKey,
                                       @PathVariable("token") String token) {

        logger.info("ssoClientProperties: {}", ssoClientProperties);
        LoginUser loginUser = new LoginUser();
        loginUser.setToken(token);
        loginUser.setId(clientKey);
        loginUser.setName(clientId);
        return RestResult.success(loginUser);
    }
}
