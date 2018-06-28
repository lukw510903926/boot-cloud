package com.cloud.gateway.util;

/**
 * @author lukw
 * @email 13507615840@163.com
 * @create 2018-06-28 14:50
 **/
public interface TokenManager {

    /**
     * 获取登录信息
     *
     * @param token
     * @return
     */
    LoginUser getLoginUser(String token);

    /**
     * 设置登录信息
     *
     * @param token
     * @param loginUser
     */
    void setLoginUser(String token, LoginUser loginUser);
}
