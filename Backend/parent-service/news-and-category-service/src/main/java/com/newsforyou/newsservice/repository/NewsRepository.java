package com.newsforyou.newsservice.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.newsforyou.newsservice.model.News;

public interface NewsRepository extends MongoRepository<News, String> {
	
	List<News> findByCategoryIdIn(List<String> categoryIds, Sort sort);

	List<News> findByCategoryIdAndAgencyId(String categoryId, String agencyId, Sort sort);
}
