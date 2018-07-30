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

    private long expireTime;

    @Override
    public void setExpireTime(long expireTime) {

        expireTime = expireTime > 0 ? expireTime : DEFAULT_EXPIRE_TIME;
        this.expireTime = expireTime;
    }

    @Override
    public LoginUser readToken(String token) {

        LoginUser loginUser = (LoginUser) redisService.get(PREFIX + token);
        this.redisService.expire(PREFIX + token, expireTime);
        return loginUser;
    }

    @Override
    public String saveToken(LoginUser loginUser) {

        String token = UUID.randomUUID().toString().replaceAll("-", "");
        loginUser.setToken(token);
        this.redisService.set(PREFIX + token, loginUser, expireTime);
        return token;
    }

    @Override
    public boolean delete(String token) {

        try {
            redisService.del(PREFIX + token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public LoginUser updateToken(String token, LoginUser loginUser) {

        redisService.set(PREFIX + token, loginUser, expireTime);
        return loginUser;
    }
}
