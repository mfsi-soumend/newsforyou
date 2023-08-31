package com.newsforyou.userservice.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.newsforyou.userservice.model.UserAdmin;

public interface UserRepository extends MongoRepository<UserAdmin, String> {

	Optional<UserAdmin> findByEmail(String email);

}
