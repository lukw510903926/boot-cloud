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

    /**
     * 系统所有资源
     */
    private SystemPermission systemPermission;

    /**
     * 存储登录的用户
     */
    private Map<String, LoginUser> securityMap = new ConcurrentHashMap<>(16);

    /**
     * 存储token 过期时间
     */
    private Map<String, Long> expireMap = new ConcurrentHashMap<>(16);

    /**
     * 默认过期时间 30分钟
     */
    private long expire = 1800 * 1000;

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

        this.expireMap.put(PREFIX + token, System.currentTimeMillis() + this.expire);
        return this.securityMap.get(PREFIX + token);
    }

    @Override
    public String saveToken(LoginUser loginUser) {

        String token = UUID.randomUUID().toString().replaceAll("-", "");
        loginUser.setToken(token);
        this.securityMap.put(PREFIX + token, loginUser);
        this.expireMap.put(PREFIX + token, System.currentTimeMillis() + this.expire);
        return token;
    }

    @Override
    public void delete(String token) {

        if (StringUtils.isNotBlank(token)) {
            this.securityMap.remove(PREFIX + token);
            this.expireMap.remove(PREFIX + token);
        }
    }

    @Override
    public LoginUser updateToken(String token, LoginUser loginUser) {

        this.expireMap.put(PREFIX + token, System.currentTimeMillis() + this.expire);
        return this.securityMap.put(PREFIX + token, loginUser);
    }

    @Override
    public void expire(String token, long time) {

        if (time < 0) {
            this.expireMap.remove(PREFIX + token);
            this.securityMap.remove(PREFIX + token);
        } else {
            this.expireMap.put(PREFIX + token, System.currentTimeMillis() + time);
        }
    }

    @Override
    public void afterPropertiesSet() {

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 延时 6 秒后，按 5 秒的周期执行任务
        scheduledExecutorService.scheduleAtFixedRate(new ExpireLoginUser(), 6000, 5000, TimeUnit.MILLISECONDS);
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
