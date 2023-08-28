package com.newsforyou.agencyservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "agency_feed")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgencyFeed {
	
	@Id
	private String agencyFeedId;
	private String agencyFeedUrl;
	private String agencyId;
	private String categoryId;
}
