package com.newsforyou.agencyservice.exception.handler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.newsforyou.agencyservice.configurations.Constants;
import com.newsforyou.agencyservice.exception.BaseException;
import com.newsforyou.agencyservice.exception.BaseRunTimeException;
import com.newsforyou.agencyservice.exception.InvalidRequestException;
import com.newsforyou.agencyservice.model.ErrorResponse;
import com.newsforyou.agencyservice.model.Response;
import com.newsforyou.agencyservice.model.SingleResponse;
import javax.servlet.http.HttpServletRequest;


@RestControllerAdvice(basePackages = {"com.newsforyou.newsservice.controller"})
public class GlobalExceptionHandler {

	/**
	 * Handles run time exception logs error message and returns standard error response
	 * 
	 * @param request
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(RuntimeException.class)
	public Response<?> handleRuntimeException(HttpServletRequest request, final RuntimeException exception) {
		Response<?> response = new Response<>();
		response.setSuccess(false);
		return response;
	}
	
	/**
	 * Handles all the exceptions which extends base Exception
	 * 
	 * @param request
	 * @param baseException
	 * @return
	 */
	@ExceptionHandler(BaseException.class)
	public Response<?> handleBaseException(HttpServletRequest request, final BaseException baseException) {
		
		Response<?> response = new Response<>();
		response.setStatus(baseException.getStatus());
		response.setSuccess(false);
		return response;
	}
	
	/**
	 * Handles base run time exception logs error message and returns standard error response
	 * 
	 * @param request
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(BaseRunTimeException.class)
	public Response<?> handleBaseRuntimeException(HttpServletRequest request, final BaseRunTimeException exception) {
		
		Response<?> response = new Response<>();
		response.setStatus(exception.getStatus());
		response.setSuccess(false);
		return response;
	}
	
	/**
	  * Handles an InvalidRequestException and generates an error response.
	  *
	  * @param exception The InvalidRequestException that was thrown
	  * @param status The HTTP status code to return in the response
	  * @return A ResponseEntity containing an error response
	  */
	@ExceptionHandler(InvalidRequestException.class)
	public ResponseEntity<Object> handleInvalidRequestException(InvalidRequestException exception,HttpStatus status) {
		
		SingleResponse<?> response = new SingleResponse<>();
		response.setError(new ErrorResponse(Constants.INVALID_REQUEST,exception.getError()));
		response.setStatus(status.value());
		response.setSuccess(false);
		return response.generateErrorResponse(status,status.value());
	}
}
