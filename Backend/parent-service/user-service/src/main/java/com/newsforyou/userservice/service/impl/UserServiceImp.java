package com.newsforyou.userservice.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.newsforyou.userservice.configurations.Constants;
import com.newsforyou.userservice.dto.UserRegisterRequest;
import com.newsforyou.userservice.exception.InvalidRequestException;
import com.newsforyou.userservice.model.UserAdmin;
import com.newsforyou.userservice.repository.UserRepository;
import com.newsforyou.userservice.service.JwtService;
import com.newsforyou.userservice.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImp implements UserService {

	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	
	@Override
	public void createUser(UserRegisterRequest userRegisterRequest) {
		if(userRegisterRequest.getName() == null || userRegisterRequest.getName().isBlank() ||
				userRegisterRequest.getEmail() == null || userRegisterRequest.getEmail().isBlank() ||
				userRegisterRequest.getPassword()==null || userRegisterRequest.getPassword().isBlank()) {
			throw new InvalidRequestException(Constants.EMPTY_DATA_ERROR);
		}
		if(!userRepo.findByEmail(userRegisterRequest.getEmail()).isEmpty()) {
			throw new InvalidRequestException(Constants.USER_ALREADY_EXSISTS);
		}
		try {
			UserAdmin newUser = UserAdmin.builder()
					.name(userRegisterRequest.getName())
					.email(userRegisterRequest.getEmail())
					.password(passwordEncoder.encode(userRegisterRequest.getPassword()))
					.build();
			userRepo.save(newUser);
			log.info("User created!! - "+userRegisterRequest.getName());
		}
		catch (Exception e) {
			throw new InvalidRequestException(e.getMessage());
		}

	}

	@Override
	public String generateToken(String email) {
		return jwtService.generateToken(email);
	}
	
	@Override
	public void validateToken(String token) {
        jwtService.validateToken(token);
    }

}
