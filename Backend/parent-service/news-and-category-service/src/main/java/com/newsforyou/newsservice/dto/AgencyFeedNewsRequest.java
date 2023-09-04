package com.newsforyou.newsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgencyFeedNewsRequest {
	private String agencyId;
	private String categoryId;

}
