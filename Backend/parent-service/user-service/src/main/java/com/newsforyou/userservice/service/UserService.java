package com.newsforyou.userservice.service;

import com.newsforyou.userservice.dto.UserRegisterRequest;

public interface UserService {
	
	public void createUser(UserRegisterRequest userRegisterRequest);

	public String generateToken(String email);

	void validateToken(String token);
}
