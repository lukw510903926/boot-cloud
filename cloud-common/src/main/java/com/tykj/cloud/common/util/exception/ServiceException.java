package com.tykj.cloud.common.util.exception;

/**
 * Service层公用的Exception, 从由Spring管理事务的函数中抛出时会触发事务回滚.
 */
public class ServiceException extends RuntimeException {

	/**
	 * 状态码
	 */
	private int code;

	private static final long serialVersionUID = 1L;

	public ServiceException() {
		super();
		this.code = 1000;
	}

	public ServiceException(String message) {
		super(message);
		this.code = 1000;
	}

	public ServiceException(Throwable cause) {
		super(cause);
		this.code = 1000;
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
		this.code = 1000;
	}

	public ServiceException setCode(int code) {
		this.code = code;
		return this;
	}

	public int getCode() {
		return code;
	}
}