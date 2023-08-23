package com.newsforyou.newsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsRequest {
	private String newsTitle;
	private String newsDescription;
	private String categoryId;
	private String agencyId;
}
