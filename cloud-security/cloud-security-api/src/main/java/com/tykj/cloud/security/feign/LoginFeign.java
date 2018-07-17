package com.tykj.cloud.security.feign;

import com.tykj.cloud.common.web.LoginUser;
import com.tykj.cloud.common.web.RestResult;
import com.tykj.cloud.security.entity.SystemUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("${cloud.api.security.serviceId}")
public interface LoginFeign {

    Logger logger = LoggerFactory.getLogger(LoginFeign.class);

    /**
     * 登录
     *
     * @param systemUser
     * @return
     */
    @PostMapping("/login")
    RestResult<LoginUser> login(@RequestBody SystemUser systemUser);

    /**
     * token 认证
     *
     * @param clientId  客户端标识
     * @param clientKey 客户端秘钥
     * @param token     认证token
     * @return
     */
    @GetMapping("/{clientId}/{clientKey}/{token}")
    RestResult<LoginUser> token(@PathVariable("clientId") String clientId, @PathVariable("clientKey") String clientKey,
                    @PathVariable("token") String token);
}
