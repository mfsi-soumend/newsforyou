package com.newsforyou.newsservice.service;

import com.newsforyou.newsservice.dto.CategoryRequest;
import com.newsforyou.newsservice.dto.CategoryResponse;
import com.newsforyou.newsservice.dto.CategoryResponseList;
import com.newsforyou.newsservice.model.Category;

public interface CategoryService {
	
	public void createCategory(CategoryRequest categoryRequest);
	
	public CategoryResponseList getAllCategory();

	public boolean checkCategoryAvailable(String categoryId);

	public void updateCategory(Category catagoryRequest);

	public CategoryResponse getSingleCategory(String categoryId);
}
