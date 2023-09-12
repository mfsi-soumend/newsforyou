package com.newsforyou.newsservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Agency {

	private String agencyId;
	private String agencyName;
	private String agencyLogoPath;
}