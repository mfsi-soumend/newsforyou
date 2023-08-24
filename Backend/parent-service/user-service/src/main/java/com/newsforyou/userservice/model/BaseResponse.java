package com.newsforyou.userservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
@Setter @Getter
public class BaseResponse {
	private Boolean success;
	private ErrorResponse error;
}