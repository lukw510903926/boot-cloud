package com.tykj.cloud.security.auth;

import com.tykj.cloud.security.util.web.LoginUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author lukew
 * @Description:
 * @email 13507615840@163.com
 * @date 2018年7月16日 下午9:30:47
 */
public class RedisSecurityManager implements SecurityManager {

    /**
     * token 过期时间
     */
    private long expireTime;

    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void setExpireTime(long expireTime) {

        expireTime = expireTime > 0 ? expireTime : DEFAULT_EXPIRE_TIME;
        this.expireTime = expireTime;
    }

    @Override
    public LoginUser readToken(String token) {

        LoginUser loginUser = (LoginUser) (StringUtils.isBlank(token) ? null : redisTemplate.opsForValue().get(PREFIX + token));
        if (loginUser != null) {
            this.expire(token, expireTime);
        }
        return loginUser;
    }

    @Override
    public String saveToken(LoginUser loginUser) {

        String token = UUID.randomUUID().toString().replaceAll("-", "");
        loginUser.setToken(token);
        this.redisTemplate.opsForValue().set(PREFIX + token, loginUser);
        this.expire(token, expireTime);
        return token;
    }

    @Override
    public void delete(String token) {

        redisTemplate.delete(PREFIX + token);
    }

    @Override
    public LoginUser updateToken(String token, LoginUser loginUser) {

        this.redisTemplate.opsForValue().set(PREFIX + token, loginUser);
        this.expire(token, expireTime);
        return loginUser;
    }

    @Override
    public void expire(String key, long time) {
        if (time > 0) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }
}
