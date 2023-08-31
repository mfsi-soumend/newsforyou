package com.newsforyou.userservice.configurations;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.newsforyou.userservice.model.UserAdmin;
import com.newsforyou.userservice.repository.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 Optional<UserAdmin> credential = userRepo.findByEmail(username);
	        return credential.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("user not found with username :" + username));
	}

}
