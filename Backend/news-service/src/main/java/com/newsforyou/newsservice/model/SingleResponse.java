package com.newsforyou.newsservice.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;

@JsonPropertyOrder({"success","statusCode","data"})
@Getter @Setter
public class SingleResponse<R> extends BaseResponse {
	@JsonInclude(Include.NON_NULL)
	private R data;
	private int status = 200;
	
	public ResponseEntity<Object> generateResponse(HttpStatus status) {
    	Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", this.getSuccess());
        map.put("error",this.getError());
        map.put("status", status.value());
        map.put("data", this.getData());
        return new ResponseEntity<Object>(map,status);
	}
	
	public ResponseEntity<Object> generateErrorResponse(HttpStatus status, int errorStatus) {
    	Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", this.getSuccess());
        map.put("error",this.getError());
        map.put("status", errorStatus);
        map.put("data", this.getData());
        return new ResponseEntity<Object>(map,status);
	}
}