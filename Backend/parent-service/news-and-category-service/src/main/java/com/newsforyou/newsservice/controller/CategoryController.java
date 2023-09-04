package com.newsforyou.newsservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.newsforyou.newsservice.configurations.Constants;
import com.newsforyou.newsservice.dto.CategoryRequest;
import com.newsforyou.newsservice.dto.CategoryResponseList;
import com.newsforyou.newsservice.exception.InvalidRequestException;
import com.newsforyou.newsservice.exception.handler.GlobalExceptionHandler;
import com.newsforyou.newsservice.model.Category;
import com.newsforyou.newsservice.model.SingleResponse;
import com.newsforyou.newsservice.service.CategoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
	
	private final CategoryService categoryService;
	private final GlobalExceptionHandler ge;

	@PostMapping
	public ResponseEntity<Object> createCatagory(@RequestBody CategoryRequest catagoryRequest) {
		try {
			categoryService.createCategory(catagoryRequest);
			SingleResponse<String> resp = new SingleResponse<>();
			resp.setData(Constants.CATEGORY_CREATED);
			resp.setSuccess(true);
			return resp.generateResponse(HttpStatus.CREATED);
		}
		catch(InvalidRequestException e) {
			return ge.handleInvalidRequestException(e, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PutMapping
	public ResponseEntity<Object> updateCatagory(@RequestBody Category catagoryRequest) {
		try {
			categoryService.updateCategory(catagoryRequest);
			SingleResponse<String> resp = new SingleResponse<>();
			resp.setData(Constants.CATEGORY_UPDATED);
			resp.setSuccess(true);
			return resp.generateResponse(HttpStatus.CREATED);
		}
		catch(InvalidRequestException e) {
			return ge.handleInvalidRequestException(e, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/all-category")
	public ResponseEntity<Object> getAllCategory() {
		SingleResponse<CategoryResponseList> resp = new SingleResponse<>();
		try {
			CategoryResponseList res = categoryService.getAllCategory();
			resp.setSuccess(true);
			resp.setData(res);
			return resp.generateResponse(HttpStatus.OK);
		}
		catch (Exception e) {
			return ge.handleInvalidRequestException(new InvalidRequestException(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/available/{category-id}")
	public boolean checkCategoryAvailable(@PathVariable("category-id") String categoryId) {
		return categoryService.checkCategoryAvailable(categoryId);
	}
}
