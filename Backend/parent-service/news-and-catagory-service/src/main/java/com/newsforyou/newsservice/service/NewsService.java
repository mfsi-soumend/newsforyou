package com.newsforyou.newsservice.service;

import com.newsforyou.newsservice.dto.NewsRequest;
import com.newsforyou.newsservice.dto.NewsResponseList;

public interface NewsService {

	public void createNews(NewsRequest newsRequest);

	public NewsResponseList getAllNews();
}
