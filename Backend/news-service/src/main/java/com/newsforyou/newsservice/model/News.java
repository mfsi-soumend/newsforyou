package com.newsforyou.newsservice.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "news")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class News {
	
	@Id
	private String newsId;
	private String newsTitle;
	private String newsDescription;
	private LocalDateTime newsPublishDateTime;
	private String newsLink;
	private long clickCount;
	private String categoryId;
	private String agencyId;
}
