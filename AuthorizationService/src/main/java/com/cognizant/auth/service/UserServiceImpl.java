package com.cognizant.auth.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.cognizant.auth.exception.InvalidCredentialsException;
import com.cognizant.auth.model.User;
import com.cognizant.auth.repository.UserRepository;

@Service
public class UserServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Value("${userDetails.errorMessage}")
	private String USER_DOES_NOT_EXIST_MESSAGE;

	/**
	 * Gets the user from the database
	 * 
	 * @param username
	 * @return User object
	 */
	private Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	/**
	 * Loads user from the database
	 * 
	 * @throws InvalidCredentialsException
	 * @return UeerDetails
	 */
	@Override
	public UserDetails loadUserByUsername(String username) {
		Optional<User> opt = findByUsername(username);
		if (opt.isEmpty()) {
			throw new InvalidCredentialsException(USER_DOES_NOT_EXIST_MESSAGE);
		} else {
			User user = opt.get();
			return new org.springframework.security.core.userdetails.User(username, user.getPassword(),
					Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
		}
	}

}
