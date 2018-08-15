package com.tykj.cloud.security.auth;


import com.tykj.cloud.security.util.web.LoginUser;

/**
 * @Description: 认证管理器
 * @author lukew
 * @email 13507615840@163.com
 * @date 2018年7月16日 下午8:25:27
 */
public interface SecurityManager {

	Long DEFAULT_EXPIRE_TIME = 1800L;

	String PREFIX = "cloud:security:";

	/**
	 * token 过期时间 单位s
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
	boolean delete(String token);

	/**
	 * 更新登录信息
	 * 
	 * @param token
	 * @param loginUser
	 * @return
	 */
	LoginUser updateToken(String token, LoginUser loginUser);
}
