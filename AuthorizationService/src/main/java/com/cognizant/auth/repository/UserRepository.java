package com.cognizant.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.auth.model.User;

/**
 * Repository for User
 * 
 * @author Sunmeet
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	Optional<User> findByUsername(String username);
}
