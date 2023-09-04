package com.newsforyou.newsservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.newsforyou.newsservice.configurations.Constants;
import com.newsforyou.newsservice.dto.AgencyFeedNewsRequest;
import com.newsforyou.newsservice.dto.NewsRequest;
import com.newsforyou.newsservice.dto.NewsResponse;
import com.newsforyou.newsservice.dto.NewsResponseList;
import com.newsforyou.newsservice.dto.SearchNewsRequest;
import com.newsforyou.newsservice.exception.InvalidRequestException;
import com.newsforyou.newsservice.exception.handler.GlobalExceptionHandler;
import com.newsforyou.newsservice.model.SingleResponse;
import com.newsforyou.newsservice.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {
	
	private final NewsService newsService;
	private final GlobalExceptionHandler ge;

	@PostMapping
	public ResponseEntity<Object> createNews(@RequestBody NewsRequest newsRequest) {
		try {
			newsService.createNews(newsRequest);
			SingleResponse<String> resp = new SingleResponse<>();
			resp.setData(Constants.NEWS_CREATED);
			resp.setSuccess(true);
			return resp.generateResponse(HttpStatus.CREATED);
		}
		catch(InvalidRequestException e) {
			return ge.handleInvalidRequestException(e, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/all-news")
	public ResponseEntity<Object> getAllNews(@RequestBody SearchNewsRequest searchNewsRequest) {
		SingleResponse<NewsResponseList> resp = new SingleResponse<>();
		try {
			NewsResponseList res = newsService.getAllNews(searchNewsRequest.getCategoryIds());
			resp.setSuccess(true);
			resp.setData(res);
			return resp.generateResponse(HttpStatus.OK);
		}
		catch (Exception e) {
			return ge.handleInvalidRequestException(new InvalidRequestException(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/agency-feed")
	public ResponseEntity<Object> getAllNewsByAgency(@RequestBody AgencyFeedNewsRequest agencyFeedNewsRequest) {
		SingleResponse<NewsResponseList> resp = new SingleResponse<>();
		try {
			NewsResponseList res = newsService.getAllNewsByAgency(agencyFeedNewsRequest);
			resp.setSuccess(true);
			resp.setData(res);
			return resp.generateResponse(HttpStatus.OK);
		}
		catch (Exception e) {
			return ge.handleInvalidRequestException(new InvalidRequestException(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{news_id}")
	public ResponseEntity<Object> getNews(@PathVariable("news_id") String newsId) {
		SingleResponse<NewsResponse> resp = new SingleResponse<>();
		try {
			NewsResponse res = newsService.getNews(newsId);
			resp.setSuccess(true);
			resp.setData(res);
			return resp.generateResponse(HttpStatus.OK);
		}
		catch (Exception e) {
			return ge.handleInvalidRequestException(new InvalidRequestException(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
}
