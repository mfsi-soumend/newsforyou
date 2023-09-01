package com.newsforyou.agencyservice.configurations;

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
	public static final String NO_CATEGORY_FOUND = "No Category Found!!";
	public static final String AGENCY_CREATED = "New Agency Created!!";
	public static final String AGENCY_ALREADY_EXSISTS = "Agency with same name already exists!!";
	public static final String NO_AGENCY_FOUND = "No Agency Found!!";
	public static final String AGENCY_FEED_CREATED = "New Agency Feed Created!!";
	public static final String AGENCY_FEED_ALREADY_EXSISTS = "Agency feed with same agency and category already exists!!";
	public static final String CATEGORY_SERVER_DOWN = "Category Server Down!! Please Contact Admin..";
	public static final String AGENCY_FEED_LINK_STARTER = "http://localhost:3000/agency-feed/";
	public static final String NO_AGENCY_FEED_FOUND = "No Agency Feed Found!!";
	public static final String AGENCY_UPDATED = "Agency Updated Succesfully";
}
