package com.newsforyou.newsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FetchNewsDto {
	
	private String agencyId;
	private String categoryId;
	private String rssUrl;
}
