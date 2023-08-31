package com.newsforyou.userservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newsforyou.userservice.configurations.Constants;
import com.newsforyou.userservice.dto.UserLoginRequest;
import com.newsforyou.userservice.dto.UserRegisterRequest;
import com.newsforyou.userservice.exception.InvalidRequestException;
import com.newsforyou.userservice.exception.handler.GlobalExceptionHandler;
import com.newsforyou.userservice.model.SingleResponse;
import com.newsforyou.userservice.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	private final GlobalExceptionHandler ge;
	private final AuthenticationManager authManager;
	
	@PostMapping
	public ResponseEntity<Object> createUser(@RequestBody UserRegisterRequest userRegisterReq) {
		try {
			userService.createUser(userRegisterReq);
			SingleResponse<String> resp = new SingleResponse<>();
			resp.setData(Constants.USER_CREATED);
			resp.setSuccess(true);
			return resp.generateResponse(HttpStatus.CREATED);
		}
		catch(InvalidRequestException e) {
			return ge.handleInvalidRequestException(e, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody UserLoginRequest loginReq) {
		Authentication authenticate = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
        if (authenticate.isAuthenticated()) {
        	SingleResponse<String> resp = new SingleResponse<>();
        	resp.setData(userService.generateToken(loginReq.getEmail()));
			resp.setSuccess(true);
            return resp.generateResponse(HttpStatus.CREATED);
        } else {
        	return ge.handleInvalidRequestException(new InvalidRequestException(Constants.INCORRECT_CREDENTIALS), HttpStatus.BAD_REQUEST);
        }
	}
	
	@GetMapping("/validate")
    public boolean validateToken(@RequestParam("token") String token) {
        userService.validateToken(token);
        return true;
    }

}
