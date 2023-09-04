package com.newsforyou.newsservice.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.newsforyou.newsservice.configurations.Constants;
import com.newsforyou.newsservice.dto.CategoryRequest;
import com.newsforyou.newsservice.dto.CategoryResponse;
import com.newsforyou.newsservice.dto.CategoryResponseList;
import com.newsforyou.newsservice.exception.InvalidRequestException;
import com.newsforyou.newsservice.model.Category;
import com.newsforyou.newsservice.repository.CategoryRepository;
import com.newsforyou.newsservice.service.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImp implements CategoryService {

	private final CategoryRepository categoryRepo;
	
	@Override
	public void createCategory(CategoryRequest categoryRequest) {
		if(categoryRequest.getCategoryTitle()== null || categoryRequest.getCategoryTitle().isBlank()) {
			throw new InvalidRequestException(Constants.EMPTY_DATA_ERROR);
		}
		if(!categoryRepo.findByCategoryTitle(categoryRequest.getCategoryTitle()).isEmpty()) {
			throw new InvalidRequestException(Constants.CATEGORY_ALREADY_EXSISTS);
		}
		try {
			Category newCategory = Category.builder()
					.categoryTitle(categoryRequest.getCategoryTitle())
					.build();
			categoryRepo.save(newCategory);
			log.info("Category created!! - "+categoryRequest.getCategoryTitle());
		}
		catch (Exception e) {
			throw new InvalidRequestException(e.getMessage());
		}
		
	}
	
	@Override
	public void updateCategory(Category categoryRequest) {
		if(categoryRequest.getCategoryTitle()== null || categoryRequest.getCategoryTitle().isBlank() ||
				categoryRequest.getCategoryId()== null || categoryRequest.getCategoryId().isBlank()) {
			throw new InvalidRequestException(Constants.EMPTY_DATA_ERROR);
		}
		Optional<Category> findById = categoryRepo.findById(categoryRequest.getCategoryId());
		List<Category> findByCategoryTitle = categoryRepo.findByCategoryTitle(categoryRequest.getCategoryTitle());
		if(!findByCategoryTitle.isEmpty() && !Objects.equals(findByCategoryTitle.get(0).getCategoryId(), categoryRequest.getCategoryId())) {
			throw new InvalidRequestException(Constants.CATEGORY_ALREADY_EXSISTS);
		}
		if(findById.isEmpty()) {
			throw new InvalidRequestException(Constants.NO_CATEGORY_FOUND);
		}
		try {
			categoryRepo.save(categoryRequest);
			log.info("Category Updated!! - "+categoryRequest.getCategoryTitle());
		}
		catch (Exception e) {
			throw new InvalidRequestException(e.getMessage());
		}
		
	}

	@Override
	public CategoryResponseList getAllCategory() {
		List<Category> allCategory = categoryRepo.findAll();
		return CategoryResponseList.builder()
				.totalCategoryCount(allCategory.size())
				.categoryResponseList(allCategory.stream().map(this::mapToCategoryList).toList())
				.build();
	}
	
	private CategoryResponse mapToCategoryList(Category category) {
		return CategoryResponse.builder()
				.categoryTitle(category.getCategoryTitle())
				.categoryId(category.getCategoryId())
				.build();
	}

	@Override
	public boolean checkCategoryAvailable(String categoryId) {
		if(categoryId == null || categoryId.isBlank()) {
			return false;
		}
		Optional<Category> findById = categoryRepo.findById(categoryId);
		if(findById.isPresent()) {
			return true;
		}
		else {
			return false;
		}
	}

}
