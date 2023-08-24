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
public class AgencyResponseList {

	private int totalAgencyCount;
	private List<AgencyResponse> agencyResponseList;
}
