package com.newsforyou.agencyservice.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgencyFeedResponseList {

	private int totalAgencyFeedCount;
	private List<AgencyFeedResponse> agencyFeedResponseList;
}
