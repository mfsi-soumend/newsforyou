package com.newsforyou.userservice.exception;

public class BaseRunTimeException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	private final Integer status;
	private final String error;
	
	public BaseRunTimeException(Integer status, String error) {
		super();
		this.status = status;
		this.error = error;
	}
	
	public Integer getStatus() {
		return status;
	}
	public String getError() {
		return error;
	}	
}