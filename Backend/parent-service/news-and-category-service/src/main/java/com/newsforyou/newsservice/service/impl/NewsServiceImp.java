package com.newsforyou.newsservice.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.newsforyou.newsservice.configurations.Constants;
import com.newsforyou.newsservice.dto.NewsRequest;
import com.newsforyou.newsservice.dto.NewsResponse;
import com.newsforyou.newsservice.dto.NewsResponseList;
import com.newsforyou.newsservice.exception.InvalidRequestException;
import com.newsforyou.newsservice.model.News;
import com.newsforyou.newsservice.repository.CategoryRepository;
import com.newsforyou.newsservice.repository.NewsRepository;
import com.newsforyou.newsservice.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsServiceImp implements NewsService {

	private final NewsRepository newsRepo;
	private final CategoryRepository categoryRepo;
	private final WebClient webClient;
	@Value("${agency_service_url}")
	private String agencyServiceUrl;
	
	@Override
	public void createNews(NewsRequest newsRequest) {
		if (newsRequest.getAgencyId() == null || newsRequest.getAgencyId().isEmpty() ||
				newsRequest.getCategoryId() == null || newsRequest.getCategoryId().isEmpty() ||
				newsRequest.getNewsDescription() == null || newsRequest.getNewsDescription().isEmpty() ||
				newsRequest.getNewsTitle() == null || newsRequest.getNewsTitle().isEmpty()) {
			throw new InvalidRequestException(Constants.EMPTY_DATA_ERROR);
		}
		if (!categoryRepo.findById(newsRequest.getCategoryId()).isPresent()) {
			throw new InvalidRequestException(Constants.NO_CATEGORY_FOUND);
		}
		boolean checkAgency = false;
 		try {
			checkAgency = webClient.get()
				.uri(agencyServiceUrl + "available/" + newsRequest.getAgencyId())
				.retrieve()
				.bodyToMono(boolean.class)
				.block();
		}
		catch (Exception e) {
			throw new InvalidRequestException(Constants.AGENCY_SERVER_DOWN);
		}
		if(!checkAgency) {
			throw new InvalidRequestException(Constants.NO_AGENCY_FOUND);
		}
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
			throw new InvalidRequestException(e.getMessage());
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
