package com.tykj.cloud.security;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import com.tykj.cloud.common.reids.RedisService;
import com.tykj.cloud.common.web.LoginUser;

/**
 * @Description:
 * @author lukew
 * @email 13507615840@163.com
 * @date 2018年7月16日 下午9:30:47
 */
public class RedisSecurityManager implements SecurityManager {

	@Autowired
	private RedisService redisService;

	private final String LOGIN_USER = "LOGIN_USER";

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
