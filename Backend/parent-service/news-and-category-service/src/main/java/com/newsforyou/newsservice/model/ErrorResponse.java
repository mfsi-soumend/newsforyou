package com.newsforyou.newsservice.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrorResponse {

	private String errorType;
	private String message;
	private Date timeStamp;
	
	/**
	 * Default Constructor
	 */
	public ErrorResponse() {}

	/**
	 * Use this constructor when you know what happened and want to use a custom error
	 */
	public ErrorResponse(Integer status) {
		super();
		this.timeStamp = new Date();
	}
	
	/**
	 * Use this constructor when you know what happened and want to use a custom error
	 */
	public ErrorResponse( String errorType,String message) {
		super();
		this.errorType = errorType;
		this.message =message;
		this.timeStamp = new Date();
	}


	/**
	 * Returns the error text.
	 * 
	 * @return String
	 */
	public String getErrorType() {
		return errorType;
	}

	/**
	 * Updates the error text.
	 * 
	 * @param error String
	 */
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	/**
	 * Returns the error message.
	 * 
	 * @return String
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Updates the error message
	 * 
	 * @param message String
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * Returns the error time stamp.
	 * 
	 * @return String
	 */
	public String getTimeStamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
		return sdf.format(this.timeStamp);
	}

	/**
	 * Updates the error time stamp.
	 * 
	 * @param timeStamp String
	 */
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
}
