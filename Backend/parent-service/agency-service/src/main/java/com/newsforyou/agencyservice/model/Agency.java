package com.newsforyou.agencyservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "agency")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Agency {
	
	@Id
	private String agencyId;
	private String agencyName;
	private String agencyLogoPath;
}
