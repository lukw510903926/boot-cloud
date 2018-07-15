package com.tykj.cloud.common.util;

import java.util.Collections;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 26223
 * @time 2016年10月14日
 * @email lukw@eastcom-sw.com
 */
public class PropertitesUtil {



	private static PropertitesUtil propertitesUtil = new PropertitesUtil();

	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = Collections.emptyMap();

	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader propertiesLoader = new PropertiesLoader("config.properties","application.properties");

	/**
	 * 重载资源属性文件
	 * @param propertiesLoader
	 */
	public static void setPropertiesLoader(PropertiesLoader propertiesLoader) {
		PropertitesUtil.propertiesLoader = propertiesLoader;
	}

	/**
	 * 获取当前实例对象
	 * @return
	 */
	public static PropertitesUtil getInstance() {
		return propertitesUtil;
	}

	/**
	 * 获取配置
	 */	
	public static String getValue(String key) {
		String value = map.get(key);
		if (value == null){
			value = propertiesLoader.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}

	public static String getValue(String key, String defaultValue) {
		String value = map.get(key);
		if (value == null) {
			value = propertiesLoader.getProperty(key, defaultValue);
			map.put(key, value);
		}
		return value;
	}

	/**
	 * 获取URL后缀
	 */
	public static String getUrlSuffix() {
		return getValue("urlSuffix");
	}
}
