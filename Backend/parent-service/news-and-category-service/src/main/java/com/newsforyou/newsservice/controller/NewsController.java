package com.newsforyou.newsservice.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.newsforyou.newsservice.configurations.Constants;
import com.newsforyou.newsservice.dto.AgencyFeedNewsRequest;
import com.newsforyou.newsservice.dto.FetchNewsDto;
import com.newsforyou.newsservice.dto.NewsRequest;
import com.newsforyou.newsservice.dto.NewsResponse;
import com.newsforyou.newsservice.dto.NewsResponseList;
import com.newsforyou.newsservice.dto.SearchNewsRequest;
import com.newsforyou.newsservice.exception.InvalidRequestException;
import com.newsforyou.newsservice.exception.handler.GlobalExceptionHandler;
import com.newsforyou.newsservice.model.SingleResponse;
import com.newsforyou.newsservice.service.NewsService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
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
	@CircuitBreaker(name = "agency", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "agency")
    @Retry(name = "agency")
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
	public String fallbackMethod(NewsRequest newsRequest, RuntimeException runtimeException) {
        return "Oops! Something went wrong, please retry after some time!";
    }
	public String fallbackMethod(RuntimeException runtimeException) {
		return "Oops! Something went wrong, please retry after some time!";
	}
	public String fallbackMethod(FetchNewsDto fetchNewsDto, RuntimeException runtimeException) {
		return "Oops! Something went wrong, please retry after some time!";
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
	
	@GetMapping("/all-news")
	@CircuitBreaker(name = "agency", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "agency")
    @Retry(name = "agency")
	public ResponseEntity<Object> getAllNewsForTable() {
		SingleResponse<NewsResponseList> resp = new SingleResponse<>();
		try {
			NewsResponseList res = newsService.getAllNewsForTable();
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
	
	@DeleteMapping("/{news_id}")
	public ResponseEntity<Object> deleteNews(@PathVariable("news_id") String newsId) {
		SingleResponse<String> resp = new SingleResponse<>();
		try {
			newsService.deleteNews(newsId);
			resp.setSuccess(true);
			resp.setData(Constants.NEWS_DELETED);
			return resp.generateResponse(HttpStatus.OK);
		}
		catch (Exception e) {
			return ge.handleInvalidRequestException(new InvalidRequestException(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/increase/{news-id}")
	public boolean increaseClickCount(@PathVariable("news-id") String newsId) {
		return newsService.increaseClickCount(newsId);
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
	@PostMapping("/rss-feed")
	@CircuitBreaker(name = "agency", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "agency")
    @Retry(name = "agency")
    public ResponseEntity<Object> fetchNewsFromRssFeed(@RequestBody FetchNewsDto fetchNewsDto) {
		try {
			newsService.fetchNewsFromRssFeed(fetchNewsDto);
			SingleResponse<String> resp = new SingleResponse<>();
			resp.setData(Constants.NEWS_CREATED);
			resp.setSuccess(true);
			return resp.generateResponse(HttpStatus.CREATED);
		}
		catch(InvalidRequestException e) {
			return ge.handleInvalidRequestException(e, HttpStatus.BAD_REQUEST);
		}
    }
}
