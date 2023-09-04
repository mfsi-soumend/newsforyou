package com.newsforyou.newsservice.service;

import java.util.List;

import com.newsforyou.newsservice.dto.AgencyFeedNewsRequest;
import com.newsforyou.newsservice.dto.NewsRequest;
import com.newsforyou.newsservice.dto.NewsResponse;
import com.newsforyou.newsservice.dto.NewsResponseList;

public interface NewsService {

	public void createNews(NewsRequest newsRequest);

	public NewsResponseList getAllNews(List<String> categoryIds);

	public NewsResponse getNews(String newsId);

	public NewsResponseList getAllNewsByAgency(AgencyFeedNewsRequest agencyFeedNewsRequest);
}
