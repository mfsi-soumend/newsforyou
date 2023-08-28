package com.newsforyou.newsservice.service;

import com.newsforyou.newsservice.dto.CategoryRequest;
import com.newsforyou.newsservice.dto.CategoryResponseList;

public interface CategoryService {
	
	public void createCategory(CategoryRequest categoryRequest);
	
	public CategoryResponseList getAllCategory();

	public boolean checkCategoryAvailable(String categoryId);
}
