package com.tykj.cloud.common.util.lock;

/**
 * Service层公用的Exception, 从由Spring管理事务的函数中抛出时会触发事务回滚.
 */
public class LockException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LockException() {
		super();
	}

	public LockException(String message) {
		super(message);
	}

	public LockException(Throwable cause) {
		super(cause);
	}

	public LockException(String message, Throwable cause) {
		super(message, cause);
	}
}