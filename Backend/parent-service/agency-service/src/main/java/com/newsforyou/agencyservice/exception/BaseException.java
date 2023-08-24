package com.newsforyou.agencyservice.exception;

public class BaseException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	 private final int status;
	 private final String error;
	
 /**
  * Constructs a new BaseException with the given status and error message.
  *
  * @param status The HTTP status code to be returned
  * @param error The error message to be returned
  */
	public BaseException(int status, String error) {
		super();
		this.error = error;
		this.status = status;
	}

	public String getError() {
		return this.error;
	}

	public int getStatus() {
		return this.status;
	}
}
