package com.newsforyou.userservice.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@JsonPropertyOrder({"success","total","data"})
@JsonInclude(Include.NON_NULL)
@Getter @Setter
public class Response<R> extends BaseResponse {

	private List<R> data;
	private Integer total;
	private int status = 200;

	public Response() {
		data = new ArrayList<>();
	}
	
	public void setData(List<R> data) {
		this.data = data==null?new ArrayList<R>():data;
		if (data!=null)
			this.total = data.size();
		else
			this.total = 0;
	}
	
	@SuppressWarnings("unchecked")
	public void addToArrayList(Object object) {
		if(object != null)
			data.add((R) object);
		
		if (data!=null)
			this.total = data.size();
		else
			this.total = 0;
	}
	

	public ResponseEntity<Object> generateResponse(HttpStatus status) {
        	Map<String, Object> map = new HashMap<>();
            map.put("success", this.getSuccess());
            map.put("error",this.getError());
            map.put("status", status.value());
            map.put("data", this.getData());
            return new ResponseEntity<>(map,status);
    }
	
	public ResponseEntity<Object> generateErrorResponse(HttpStatus status, int errorStatus) {
    	Map<String, Object> map = new HashMap<>();
        map.put("success", this.getSuccess());
        map.put("error",this.getError());
        map.put("status", errorStatus);
        map.put("data", this.getData());
        return new ResponseEntity<>(map,status);
	}
}