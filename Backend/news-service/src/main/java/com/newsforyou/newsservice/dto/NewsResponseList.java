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
public class NewsResponseList {
	private int totalNewsCount;
	private List<NewsResponse> newsList;
}
