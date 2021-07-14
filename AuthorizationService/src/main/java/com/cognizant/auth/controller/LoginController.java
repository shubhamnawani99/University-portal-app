package com.cognizant.auth.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.auth.exception.InvalidCredentialsException;
import com.cognizant.auth.util.JwtUtil;
import com.cognizant.auth.util.UserRequest;

@RestController
public class LoginController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtil jwtUtil;

	/**
	 * @URL: http://localhost:8090/auth/login
	 * 
	 * @Data: [Student] { "username": "06714802717", "password": "password" }
	 * @Data: [Admin] { "username": "916830", "password": "adminpass@1234" }
	 * 
	 * @param userRequest
	 * @return token on successful login else throws exception handled by
	 *         GlobalExceptionHandler
	 */
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody @Valid UserRequest userRequest) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
		} catch (BadCredentialsException | DisabledException | LockedException e) {
			throw new InvalidCredentialsException(e.getMessage());
		}
		String token = jwtUtil.generateToken(userRequest.getUsername());
		return new ResponseEntity<>(token, HttpStatus.OK);
	}
	
	@GetMapping(value = "/statusCheck")
	public String statusCheck() {
		return "OK";
	}
}
