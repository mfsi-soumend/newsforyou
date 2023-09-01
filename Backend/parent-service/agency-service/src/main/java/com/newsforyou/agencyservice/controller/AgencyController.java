package com.newsforyou.agencyservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.newsforyou.agencyservice.configurations.Constants;
import com.newsforyou.agencyservice.dto.AgencyRequest;
import com.newsforyou.agencyservice.dto.AgencyResponse;
import com.newsforyou.agencyservice.dto.AgencyResponseList;
import com.newsforyou.agencyservice.exception.InvalidRequestException;
import com.newsforyou.agencyservice.exception.handler.GlobalExceptionHandler;
import com.newsforyou.agencyservice.model.Agency;
import com.newsforyou.agencyservice.model.SingleResponse;
import com.newsforyou.agencyservice.service.AgencyService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/agency")
@RequiredArgsConstructor
public class AgencyController {
	
	private final AgencyService agencyService;
	private final GlobalExceptionHandler ge;
	
	@PostMapping
	public ResponseEntity<Object> createAgency(@RequestBody AgencyRequest agencyRequest) {
		try {
			agencyService.createAgency(agencyRequest);
			SingleResponse<String> resp = new SingleResponse<>();
			resp.setData(Constants.AGENCY_CREATED);
			resp.setSuccess(true);
			return resp.generateResponse(HttpStatus.CREATED);
		}
		catch(InvalidRequestException e) {
			return ge.handleInvalidRequestException(e, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PutMapping
	public ResponseEntity<Object> updateAgency(@RequestBody Agency agency) {
		try {
			agencyService.updateAgency(agency);
			SingleResponse<String> resp = new SingleResponse<>();
			resp.setData(Constants.AGENCY_UPDATED);
			resp.setSuccess(true);
			return resp.generateResponse(HttpStatus.OK);
		}
		catch(InvalidRequestException e) {
			return ge.handleInvalidRequestException(e, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/{agency-id}")
	public ResponseEntity<Object> getAgency(@PathVariable("agency-id") String agencyId) {
		SingleResponse<AgencyResponse> resp = new SingleResponse<>();
		try {
			AgencyResponse res = agencyService.getAgency(agencyId);
			resp.setSuccess(true);
			resp.setData(res);
			return resp.generateResponse(HttpStatus.OK);
		}
		catch(InvalidRequestException e) {
			return ge.handleInvalidRequestException(e, HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return ge.handleInvalidRequestException(new InvalidRequestException(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/available/{agency-id}")
	public boolean checkAgencyAvailable(@PathVariable("agency-id") String agencyId) {
		return agencyService.checkAgencyAvailable(agencyId);
	}
	
	@GetMapping("/all-agency")
	public ResponseEntity<Object> getAllAgency() {
		SingleResponse<AgencyResponseList> resp = new SingleResponse<>();
		try {
			AgencyResponseList res = agencyService.getAllAgency();
			resp.setSuccess(true);
			resp.setData(res);
			return resp.generateResponse(HttpStatus.OK);
		}
		catch (Exception e) {
			return ge.handleInvalidRequestException(new InvalidRequestException(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

}
