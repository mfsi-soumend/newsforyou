package com.newsforyou.newsservice.configurations;

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
	
	
	public static final String NEWS_CREATED = "News Successfully published";
	public static final String INVALID_REQUEST = "Invalid Request";
	public static final String INTERNAL_SERVER_ERROR = "Internal server error !!";
	public static final String NEWS_LINK_STARTER = "http://localhost:3000/news/";
	public static final String CATEGORY_CREATED = "Category Successfully created";
	public static final String CATEGORY_ALREADY_EXSISTS = "Category already exists!!";
	public static final String EMPTY_DATA_ERROR = "All Fields are required!!";
	public static final String NO_CATEGORY_FOUND = "No Category Found!!";
	public static final String NO_AGENCY_FOUND = "No Agency Found!!";
	public static final String AGENCY_SERVER_DOWN = "Agency Server Down!! Please Contact Admin..";
	public static final String NO_NEWS_FOUND = "No News Found !!";
	public static final String CATEGORY_UPDATED = "Category Successfully Updated !!";
	public static final String NEWS_DELETED = "News Deleted successfully";
}
