package com.tykj.cloud.security.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 接入认证的客户端
 * @Description:
 * @author lukew
 * @email 13507615840@163.com
 * @date 2018年7月16日 下午8:22:38
 */
@Table(name = "t_system_client")
public class SystemClient implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	/**
	 * 系统名称
	 */
	@Column(name = "name")
	private String name;
	
	/**
	 * 系统认证密码
	 */
	@Column(name = "client_key")
	private String clientKey;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClientKey() {
		return clientKey;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}
}
