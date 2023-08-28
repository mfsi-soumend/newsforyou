package com.newsforyou.agencyservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgencyFeedResponse {

	private String agencyFeedId;
	private String agencyFeedUrl;
	private String agencyId;
	private String categoryId;
}
