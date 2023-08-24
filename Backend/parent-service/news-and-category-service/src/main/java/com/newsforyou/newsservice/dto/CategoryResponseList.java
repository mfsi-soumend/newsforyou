package com.newsforyou.newsservice.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseList {

	private int totalCategoryCount;
	private List<CategoryResponse> categoryResponseList;
}
