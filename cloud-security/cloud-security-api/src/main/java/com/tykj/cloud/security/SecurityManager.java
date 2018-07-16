package com.tykj.cloud.security;

import com.tykj.cloud.common.web.LoginUser;

/**
 * @Description: 认证管理器
 * @author lukew
 * @email 13507615840@163.com
 * @date 2018年7月16日 下午8:25:27
 */
public interface SecurityManager {

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
