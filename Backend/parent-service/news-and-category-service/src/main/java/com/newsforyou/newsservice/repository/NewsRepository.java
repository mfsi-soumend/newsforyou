package com.newsforyou.newsservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.newsforyou.newsservice.model.News;

public interface NewsRepository extends MongoRepository<News, String> {

}
