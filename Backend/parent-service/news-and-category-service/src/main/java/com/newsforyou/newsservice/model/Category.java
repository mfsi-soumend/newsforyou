package com.newsforyou.newsservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "category")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {

	@Id
	private String categoryId;
	private String categoryTitle;
	
}
