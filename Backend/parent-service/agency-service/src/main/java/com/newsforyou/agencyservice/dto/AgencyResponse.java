package com.newsforyou.agencyservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgencyResponse {

	private String agencyId;
	private String agencyName;
	private String agencyLogoPath;
}
