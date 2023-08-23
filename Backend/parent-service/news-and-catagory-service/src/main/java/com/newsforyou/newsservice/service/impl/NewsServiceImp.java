package com.newsforyou.newsservice.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.newsforyou.newsservice.configurations.Constants;
import com.newsforyou.newsservice.dto.NewsRequest;
import com.newsforyou.newsservice.dto.NewsResponse;
import com.newsforyou.newsservice.dto.NewsResponseList;
import com.newsforyou.newsservice.exception.InvalidRequestException;
import com.newsforyou.newsservice.model.News;
import com.newsforyou.newsservice.repository.NewsRepository;
import com.newsforyou.newsservice.service.NewsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsServiceImp implements NewsService {

	private final NewsRepository newsRepo;
	
	@Override
	public void createNews(NewsRequest newsRequest) {
		try {
			News newNews = News.builder()
					.newsTitle(newsRequest.getNewsTitle())
					.newsDescription(newsRequest.getNewsDescription())
					.categoryId(newsRequest.getCategoryId())
					.agencyId(newsRequest.getAgencyId())
					.clickCount(0)
					.newsPublishDateTime(LocalDateTime.now())
					.build();
			News savedNews = newsRepo.save(newNews);
			savedNews.setNewsLink(Constants.NEWS_LINK_STARTER + savedNews.getNewsId());
			savedNews = newsRepo.save(savedNews);
			log.info("News Published!! - "+savedNews.getNewsTitle());
		}
		catch (Exception e) {
			throw new InvalidRequestException(Constants.INVALID_REQUEST);
		}
	}

	@Override
	public NewsResponseList getAllNews() {
		List<News> allNews = newsRepo.findAll();
		return NewsResponseList.builder()
				.totalNewsCount(allNews.size())
				.newsList(allNews.stream().map(this::mapToNewsList).toList())
				.build();
	}
	
	private NewsResponse mapToNewsList(News news) {
		return NewsResponse.builder()
				.newsId(news.getNewsId())
				.newsTitle(news.getNewsTitle())
				.newsDescription(news.getNewsDescription())
				.categoryId(news.getCategoryId())
				.agencyId(news.getAgencyId())
				.clickCount(news.getClickCount())
				.newsPublishDateTime(news.getNewsPublishDateTime())
				.newsLink(news.getNewsLink())
				.build();
	}

}
