package com.newsforyou.newsservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.newsforyou.newsservice.model.Category;

public interface CategoryRepository extends MongoRepository<Category, String>{

	List<Category> findByCategoryTitle(String categoryTitle);

}
