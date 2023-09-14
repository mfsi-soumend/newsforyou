package com.newsforyou.agencyservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.newsforyou.agencyservice.configurations.Constants;
import com.newsforyou.agencyservice.dto.AgencyFeedRequest;
import com.newsforyou.agencyservice.dto.AgencyFeedResponse;
import com.newsforyou.agencyservice.dto.AgencyFeedResponseList;
import com.newsforyou.agencyservice.exception.InvalidRequestException;
import com.newsforyou.agencyservice.exception.handler.GlobalExceptionHandler;
import com.newsforyou.agencyservice.model.SingleResponse;
import com.newsforyou.agencyservice.service.AgencyFeedService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/agency-feed")
@RequiredArgsConstructor
public class AgencyFeedController {

	private final AgencyFeedService agencyFeedService;
	private final GlobalExceptionHandler ge;
	
	@PostMapping
	@CircuitBreaker(name = "news", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "news")
    @Retry(name = "news")
	public ResponseEntity<Object> createAgencyFeed(@RequestBody AgencyFeedRequest agencyFeedRequest) {
		try {
			agencyFeedService.createAgencyFeed(agencyFeedRequest);
			SingleResponse<String> resp = new SingleResponse<>();
			resp.setData(Constants.AGENCY_FEED_CREATED);
			resp.setSuccess(true);
			return resp.generateResponse(HttpStatus.CREATED);
		}
		catch(InvalidRequestException e) {
			return ge.handleInvalidRequestException(e, HttpStatus.BAD_REQUEST);
		}
	}
	
	public String fallbackMethod(AgencyFeedRequest agencyFeedRequest, RuntimeException runtimeException) {
        return "Oops! Something went wrong, please retry after some time!";
    }
	
	@GetMapping("/all-agency-feed")
	public ResponseEntity<Object> getAllAgencyFeed() {
		SingleResponse<AgencyFeedResponseList> resp = new SingleResponse<>();
		try {
			AgencyFeedResponseList res = agencyFeedService.getAllAgencyFeed();
			resp.setSuccess(true);
			resp.setData(res);
			return resp.generateResponse(HttpStatus.OK);
		}
		catch (Exception e) {
			return ge.handleInvalidRequestException(new InvalidRequestException(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{agency-feed-id}")
	public ResponseEntity<Object> getAgencyFeed(@PathVariable("agency-feed-id") String agencyFeedId) {
		SingleResponse<AgencyFeedResponse> resp = new SingleResponse<>();
		try {
			AgencyFeedResponse res = agencyFeedService.getAgencyFeed(agencyFeedId);
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
}
