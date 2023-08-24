package com.newsforyou.agencyservice.service;

import com.newsforyou.agencyservice.dto.AgencyRequest;
import com.newsforyou.agencyservice.dto.AgencyResponse;
import com.newsforyou.agencyservice.dto.AgencyResponseList;

public interface AgencyService {
	
	public void createAgency(AgencyRequest agencyRequest);

	public AgencyResponseList getAllAgency();

	public AgencyResponse getAgency(String agencyId);

	public boolean checkAgencyAvailable(String agencyId);
}
