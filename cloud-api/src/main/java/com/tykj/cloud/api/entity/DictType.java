package com.tykj.cloud.api.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
* @ClassName: DictType 
* @Description: 数据枚举字典
* @author lukew
* @email 13507615840@163.com
* @date 2017年12月5日 下午9:21:58
 */
public class DictType implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	
	/**
	 * 字典名称
	 */
	private String name;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 修改时间
	 */
	private Date modified;

	/**
	 * 修改人
	 */
	private String modifier;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
}
