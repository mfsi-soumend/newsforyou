package com.newsforyou.newsservice.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsResponse {
	private String newsId;
	private String newsTitle;
	private String newsDescription;
	private LocalDateTime newsPublishDateTime;
	private String newsLink;
	private long clickCount;
	private String categoryId;
	private String agencyId;
}
