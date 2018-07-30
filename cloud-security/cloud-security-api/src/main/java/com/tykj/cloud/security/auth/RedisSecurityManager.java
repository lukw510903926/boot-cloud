package com.tykj.cloud.security.auth;

import com.tykj.cloud.common.reidis.RedisService;
import com.tykj.cloud.common.web.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * @Description:
 * @author lukew
 * @email 13507615840@163.com
 * @date 2018年7月16日 下午9:30:47
 */
public class RedisSecurityManager implements SecurityManager {

	@Autowired
	private RedisService redisService;

	@Override
	public LoginUser readToken(String token) {
		
		return (LoginUser) redisService.hget(LOGIN_USER, token);
	}

	@Override
	public String saveToken(LoginUser loginUser) {

		String hashKey = UUID.randomUUID().toString().replaceAll("-", "");
		loginUser.setToken(hashKey);
		redisService.hset(LOGIN_USER, hashKey, loginUser);
		return hashKey;
	}

	@Override
	public boolean delete(String token) {

		try {
			redisService.hdel(LOGIN_USER, token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public LoginUser updateToken(String token, LoginUser loginUser) {
		
		redisService.hset(LOGIN_USER, token, loginUser);
		return loginUser;
	}
}
