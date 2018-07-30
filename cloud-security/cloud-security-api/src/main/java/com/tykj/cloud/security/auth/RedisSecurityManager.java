package com.tykj.cloud.security.auth;

import com.tykj.cloud.common.reidis.RedisService;
import com.tykj.cloud.common.web.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * @author lukew
 * @Description:
 * @email 13507615840@163.com
 * @date 2018年7月16日 下午9:30:47
 */
public class RedisSecurityManager implements SecurityManager {

    @Autowired
    private RedisService redisService;

    private Long expireTime;

    @Override
    public void setExpireTime(Long expireTime) {

        expireTime = expireTime == null ? DEFAULT_EXPIRE_TIME : expireTime;
        this.expireTime = expireTime;
    }

    @Override
    public LoginUser readToken(String token) {

        LoginUser loginUser = (LoginUser) redisService.get(token);
        this.redisService.expire(token, expireTime);
        return loginUser;
    }

    @Override
    public String saveToken(LoginUser loginUser) {

        String key = PREFIX + UUID.randomUUID().toString().replaceAll("-", "");
        loginUser.setToken(key);
        this.redisService.set(key, loginUser, expireTime);
        return key;
    }

    @Override
    public boolean delete(String token) {

        try {
            redisService.del(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public LoginUser updateToken(String token, LoginUser loginUser) {

        redisService.set(token, loginUser, expireTime);
        return loginUser;
    }
}
