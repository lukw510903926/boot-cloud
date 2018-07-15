package com.tykj.cloud.common.web;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @project : tykj-common
 * @createTime : 2017年7月7日 : 下午2:10:22
 * @author : lukewei
 * @description : restful 请求结果参数封装
 */
public class RestResult<T> implements Serializable{

	private static final long serialVersionUID = 6754864327735826545L;

	/**
	 * 状态码
	 */
	private Integer status;
	
	/**
	 * 消息
	 */
	private String msg;

	/**
	 * 是否成功
	 */
	private boolean success;
	
	/**
	 * 请求结果
	 */
	private T data;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public RestResult() {
	}

	public RestResult(ResultCode resultCode, String msg,T data) {

		this.status = resultCode.getCode();
		if(StringUtils.isBlank(msg)) {
			this.msg = resultCode.getMsg();
		}else {
			this.msg = msg;
		}
		this.success = resultCode.isSuccess();
		this.data = data;
	}

	public RestResult(Integer status,String msg,boolean success, T data) {

		this.status = status;
		this.msg = msg;
		this.success = success;
		this.data = data;
	}
	
	public RestResult(Integer status,String msg,boolean success) {

		this.status = status;
		this.msg = msg;
		this.success = success;
	}
	
	public RestResult(ResultCode resultCode, T data) {
		
		this.status = resultCode.getCode();
		this.msg = resultCode.getMsg();
		this.success = resultCode.isSuccess();
		this.data = data;
	}

	/**
	 * 请求成功
	 * @param <T>
	 * @param object
	 * @return
	 */
	public static <T> RestResult<T> success(T object){

		return new RestResult<T>(ResultCode.SUCCESS,object);
	}

	/**
	 * 请求失败
	 * @param object
	 * @param msg
	 * @return
	 */
	public static <T> RestResult<T> fail(T object,String msg){

		return new RestResult<T>(ResultCode.INTERNAL_SERVER_ERROR,msg,object);
	}
	
	/**
	 * 参数问题
	 * @param object
	 * @param msg
	 * @return
	 */
	public static <T> RestResult<T> parameter(T object,String msg){

		return new RestResult<T>(ResultCode.PARAMETER_ERROR,msg,object);
	}
	
	public static <T> RestResult<T> unAuthorized(String msg) {
		
		if(StringUtils.isBlank(msg)) {
			return new RestResult<>(ResultCode.UNAUTHORIZED, "认证失败,请登录!",null);
		}
		return new RestResult<>(ResultCode.UNAUTHORIZED, msg,null);
	}
	
	public static <T> RestResult<T> unAuthorized() {
		
		return unAuthorized(null);
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

	public enum ResultCode {

		SUCCESS(200, "请求成功",true),
		
		FAIL(400, "请求失败",false),
		
		UNAUTHORIZED(401, "认证失败",false),
		
		NOT_FOUND(404, "接口不存在",false),
		
		PARAMETER_ERROR(1001,"参数不全",false) ,
		
		INTERNAL_SERVER_ERROR(500, "服务器内部错误",false);

		private int code;

		private String msg;

		private boolean success ;

		ResultCode(int code, String msg,boolean success) {
			this.code = code;
			this.msg = msg;
			this.success = success;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}
	}
	
}
