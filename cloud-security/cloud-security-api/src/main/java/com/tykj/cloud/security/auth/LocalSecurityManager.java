package com.tykj.cloud.security.auth;

import com.tykj.cloud.security.util.web.LoginUser;
import com.tykj.cloud.security.util.web.SystemPermission;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 基于内存的安全管理器
 *
 * @author lukew
 * @eamil 13507615840@163.com
 * @create 2018-10-11 22:05
 **/

public class LocalSecurityManager implements SecurityManager, InitializingBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private SystemPermission systemPermission;

    private Map<String, LoginUser> securityMap = new ConcurrentHashMap<>(16);

    private Map<String, Long> expireMap = new ConcurrentHashMap<>(16);

    private long expire = 3600 * 1000;

    @Override
    public void setSystemPermission(SystemPermission systemPermission) {
        this.systemPermission = systemPermission;
    }

    @Override
    public SystemPermission getSystemPermission() {
        return this.systemPermission;
    }

    @Override
    public void setExpireTime(long expireTime) {

        this.expire = expireTime;
    }

    @Override
    public LoginUser readToken(String token) {

        this.expireMap.put(token, System.currentTimeMillis() + this.expire);
        return this.securityMap.get(token);
    }

    @Override
    public String saveToken(LoginUser loginUser) {

        String token = UUID.randomUUID().toString().replaceAll("-", "");
        loginUser.setToken(token);
        this.securityMap.put(token, loginUser);
        this.expireMap.put(token, System.currentTimeMillis() + this.expire);
        return token;
    }

    @Override
    public void delete(String token) {

        if (StringUtils.isNotBlank(token)) {
            this.securityMap.remove(token);
            this.expireMap.remove(token);
        }
    }

    @Override
    public LoginUser updateToken(String token, LoginUser loginUser) {

        this.expireMap.put(token, System.currentTimeMillis() + this.expire);
        return this.securityMap.put(token, loginUser);
    }

    @Override
    public void expire(String key, long time) {

        this.expireMap.put(key, System.currentTimeMillis() + time);
    }

    @Override
    public void afterPropertiesSet() {

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 延时 1 秒后，按 3 秒的周期执行任务
        scheduledExecutorService.scheduleAtFixedRate(new ExpireLoginUser(), 60000, 5000, TimeUnit.MILLISECONDS);
    }

    class ExpireLoginUser implements Runnable {

        @Override
        public void run() {

            if (MapUtils.isNotEmpty(expireMap)) {
                expireMap.forEach((key, value) -> {
                    if (value < System.currentTimeMillis()) {
                        expireMap.remove(key);
                        securityMap.remove(key);
                        logger.info("{} 已过期 ", key);
                    }
                });
            }
        }
    }
}
