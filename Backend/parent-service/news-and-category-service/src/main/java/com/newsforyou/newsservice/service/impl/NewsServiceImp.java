package com.newsforyou.newsservice.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newsforyou.newsservice.configurations.Constants;
import com.newsforyou.newsservice.dto.AgencyFeedNewsRequest;
import com.newsforyou.newsservice.dto.FetchNewsDto;
import com.newsforyou.newsservice.dto.NewsRequest;
import com.newsforyou.newsservice.dto.NewsResponse;
import com.newsforyou.newsservice.dto.NewsResponseList;
import com.newsforyou.newsservice.exception.InvalidRequestException;
import com.newsforyou.newsservice.model.Agency;
import com.newsforyou.newsservice.model.News;
import com.newsforyou.newsservice.repository.CategoryRepository;
import com.newsforyou.newsservice.repository.NewsRepository;
import com.newsforyou.newsservice.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsServiceImp implements NewsService {

	private final NewsRepository newsRepo;
	private final CategoryRepository categoryRepo;
	private final RssFeedService rssFeedService;
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
	public void fetchNewsFromRssFeed(FetchNewsDto fetchNewsDto) {
		if (fetchNewsDto.getAgencyId() == null || fetchNewsDto.getAgencyId().isEmpty() ||
				fetchNewsDto.getCategoryId() == null || fetchNewsDto.getCategoryId().isEmpty() ||
				fetchNewsDto.getRssUrl() == null || fetchNewsDto.getRssUrl().isEmpty() ) {
			throw new InvalidRequestException(Constants.EMPTY_DATA_ERROR);
		}
		if (!categoryRepo.findById(fetchNewsDto.getCategoryId()).isPresent()) {
			throw new InvalidRequestException(Constants.NO_CATEGORY_FOUND);
		}
		boolean checkAgency = false;
 		try {
			checkAgency = webClient.get()
				.uri(agencyServiceUrl + "available/" + fetchNewsDto.getAgencyId())
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
			Mono<List<News>> monoNewsList = rssFeedService.fetchAndParseRssFeed(fetchNewsDto.getRssUrl());
			List<News> newsList = monoNewsList.block();
			if(newsList.size()==0) {
				throw new Exception(Constants.NO_NEWS_FOUND);
			}
			newsList.forEach(news->{
				if(newsRepo.findByNewsLink(news.getNewsLink()).isEmpty()) {
					News newNews = news;
					newNews.setAgencyId(fetchNewsDto.getAgencyId());
					newNews.setCategoryId(fetchNewsDto.getCategoryId());
					newsRepo.save(newNews);
				}
			});
		}
		catch (Exception e) {
			throw new InvalidRequestException(e.getMessage());
		}
		
	}

	@Override
	public NewsResponseList getAllNews(List<String> categoryIds) {
		Sort sort = Sort.by(Sort.Direction.DESC, "newsPublishDateTime");
		List<News> allNews = categoryIds.size()!=0? newsRepo.findByCategoryIdIn(categoryIds, sort):newsRepo.findAll(sort);
		return NewsResponseList.builder()
				.totalNewsCount(allNews.size())
				.newsList(allNews.stream().map(this::mapToNewsList).toList())
				.build();
	}
	
	@Override
	public NewsResponseList getAllNewsForTable() {
		Sort sort = Sort.by(Sort.Direction.DESC, "newsPublishDateTime");
	    List<News> allNews = newsRepo.findAll(sort);
	    
	    // Use WebClient to retrieve agency data asynchronously
	    List<Agency> allAgency = webClient.get()
	            .uri(agencyServiceUrl + "all-agency")
	            .retrieve()
	            .bodyToMono(String.class)
	            .map(this::parseJsonResponse)
	            .block();
	    Map<String, String> agencyMap = createAgencyIdToNameMap(allAgency);
	    // Map News entities to NewsResponse objects with agency names
	    List<NewsResponse> newsResponseList = allNews.stream()
	            .map(news -> mapToNewsWithNames(news, agencyMap))
	            .toList();
	    
	    return NewsResponseList.builder()
	            .totalNewsCount(allNews.size())
	            .newsList(newsResponseList)
	            .build();
	}
	
	@Override
	public NewsResponseList getAllNewsByAgency(AgencyFeedNewsRequest agencyFeedNewsRequest) {
		Sort sort = Sort.by(Sort.Direction.DESC, "newsPublishDateTime");
		List<News> allNews = newsRepo.findByCategoryIdAndAgencyId(agencyFeedNewsRequest.getCategoryId(),agencyFeedNewsRequest.getAgencyId(), sort);
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
	
	private Map<String, String> createAgencyIdToNameMap(List<Agency> allAgency) {
	    Map<String, String> agencyIdToNameMap = new HashMap<>();
	    for (Agency agency : allAgency) {
	        agencyIdToNameMap.put(agency.getAgencyId(), agency.getAgencyName());
	    }
	    
	    return agencyIdToNameMap;
	}
	
	private NewsResponse mapToNewsWithNames(News news, Map<String, String> agencyMap) {
		String agencyName = agencyMap.get(news.getAgencyId());
		
		return NewsResponse.builder()
				.newsId(news.getNewsId())
				.newsTitle(news.getNewsTitle())
				.newsDescription(news.getNewsDescription())
				.categoryId(categoryRepo.findById(news.getCategoryId()).get().getCategoryTitle())
				.agencyId(agencyName != null ? agencyName : news.getAgencyId())
				.clickCount(news.getClickCount())
				.newsPublishDateTime(news.getNewsPublishDateTime())
				.newsLink(news.getNewsLink())
				.build();
	}

	@Override
	public NewsResponse getNews(String newsId) {
		if(newsId == null || newsId.isBlank()) {
			throw new InvalidRequestException(Constants.EMPTY_DATA_ERROR);
		}
		Optional<News> findById = newsRepo.findById(newsId);
		if(findById.isPresent()) {
			News news = findById.get();
			news.setClickCount(news.getClickCount()+1);
			news = newsRepo.save(news);
			news.setCategoryId(categoryRepo.findById(news.getCategoryId()).get().getCategoryTitle());
			return mapToNewsList(findById.get());
		}
		else {
			throw new InvalidRequestException(Constants.NO_NEWS_FOUND);
		}
	}
	
	@Override
	public void deleteNews(String newsId) {
		if(newsId == null || newsId.isBlank()) {
			throw new InvalidRequestException(Constants.EMPTY_DATA_ERROR);
		}
		Optional<News> findById = newsRepo.findById(newsId);
		if(findById.isPresent()) {
			newsRepo.deleteById(newsId);
		}
		else {
			throw new InvalidRequestException(Constants.NO_NEWS_FOUND);
		}
	}
	
	private List<Agency> parseJsonResponse(String jsonResponse) {
	    ObjectMapper objectMapper = new ObjectMapper();
	    
	    try {
	        JsonNode rootNode = objectMapper.readTree(jsonResponse);
	        JsonNode agencyList = rootNode.path("data").path("agencyResponseList");
	        
	        if (agencyList.isArray()) {
	            List<Agency> agencies = new ArrayList<>();
	            
	            for (JsonNode agencyNode : agencyList) {
	                Agency agency = objectMapper.convertValue(agencyNode, Agency.class);
	                agencies.add(agency);
	            }
	            
	            return agencies;
	        }
	    } catch (IOException e) {
	        e.printStackTrace(); // Handle the exception appropriately
	    }
	    
	    return Collections.emptyList(); // Return an empty list if parsing fails
	}

	@Override
	public boolean increaseClickCount(String newsId) {
		if(newsId == null || newsId.isBlank()) {
			throw new InvalidRequestException(Constants.EMPTY_DATA_ERROR);
		}
		Optional<News> findById = newsRepo.findById(newsId);
		if(findById.isPresent()) {
			News news = findById.get();
			news.setClickCount(news.getClickCount() + 1);
			newsRepo.save(news);
			return true;
		}
		else {
			throw new InvalidRequestException(Constants.NO_NEWS_FOUND);
		}
	}


}
