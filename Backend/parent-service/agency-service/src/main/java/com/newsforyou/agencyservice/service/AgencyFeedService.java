package com.newsforyou.agencyservice.service;

import com.newsforyou.agencyservice.dto.AgencyFeedRequest;
import com.newsforyou.agencyservice.dto.AgencyFeedResponse;
import com.newsforyou.agencyservice.dto.AgencyFeedResponseList;

public interface AgencyFeedService {

	void createAgencyFeed(AgencyFeedRequest agencyFeedRequest);

	AgencyFeedResponseList getAllAgencyFeed();

	AgencyFeedResponse getAgencyFeed(String agencyFeedId);

}
