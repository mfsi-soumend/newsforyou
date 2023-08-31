package com.newsforyou.userservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAdmin {
	
	@Id
	private String userId;
	private String name;
	private String email;
	private String password;
	
}
