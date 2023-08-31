package com.newsforyou.userservice.configurations;

/**
 * A class containing constants used throughout the application.
 * Constants include HTTP status codes, error messages, success messages, and entity names.
 * This class cannot be extended.
 */
public class Constants {
	
 /**
  * The Constants class is a utility class that contains only constants and should not be extended.
  * This constructor throws an IllegalStateException to prevent the class from being extended.
  *
  * @throws IllegalStateException if the Constants class is attempted to be extended
  */
	private Constants() {
		throw new IllegalStateException("Constants class cannot be extended.");
	}
	
	public static final int NOT_FOUND = 404;
	public static final int BAD_REQUEST = 400;
	public static final int ERROR_STATUS = 500;
	public static final int UNAUTHORIZED = 403;
	
	public static final String INVALID_REQUEST = "Invalid Request";
	public static final String INTERNAL_SERVER_ERROR = "Internal server error !!";
	public static final String EMPTY_DATA_ERROR = "All Fields are required!!";
	public static final String USER_CREATED = "New User Successfully Registered !!";
	public static final String USER_ALREADY_EXSISTS = "User is already registered !! Please try login..";
	public static final String INCORRECT_CREDENTIALS = "Incorrect Credentials!!";
}
