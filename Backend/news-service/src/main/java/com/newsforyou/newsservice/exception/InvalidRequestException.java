package com.newsforyou.newsservice.exception;

import com.newsforyou.newsservice.configurations.Constants;

public class InvalidRequestException extends BaseRunTimeException{

	private static final long serialVersionUID = 1L;
	
 /**
  * Constructs a new InvalidRequestException with the specified error message.
  *
  * @param error The error message to be associated with this exception
  */
	public InvalidRequestException(String error) {
		super(Constants.BAD_REQUEST, error);
	}
	
}
