package com.tykj.cloud.security.auth;


import com.tykj.cloud.security.util.web.LoginUser;
import com.tykj.cloud.security.util.web.SystemPermission;

/**
 * @author lukew
 * @Description: 认证管理器
 * @email 13507615840@163.com
 * @date 2018年7月16日 下午8:25:27
 */
public interface SecurityManager {

    Long DEFAULT_EXPIRE_TIME = 1800L;

    String PREFIX = "cloud:security:";

    String SYS_PREFIX = "sys:cloud:security:";

    /**
     * 保存系统资源
     *
     * @param SystemPermission
     */
    void setSystemPermission(SystemPermission SystemPermission);

    /**
     * 获取系统资源
     *
     * @return
     */
    SystemPermission getSystemPermission();


    /**
     * token 过期时间 单位s
     *
     * @param expireTime
     */
    void setExpireTime(long expireTime);

    /**
     * 根据token 获取登录信息
     *
     * @param token
     * @return
     */
    LoginUser readToken(String token);

    /**
     * 保存登录信息
     *
     * @param loginUser
     * @return token
     */
    String saveToken(LoginUser loginUser);

    /**
     * 删除token
     *
     * @param token
     * @return
     */
    void delete(String token);

    /**
     * 更新登录信息
     *
     * @param token
     * @param loginUser
     * @return
     */
    LoginUser updateToken(String token, LoginUser loginUser);

    /**
     * 设定key过期 单位秒
     *
     * @param key
     * @param time
     */
    void expire(String key, long time);
}
