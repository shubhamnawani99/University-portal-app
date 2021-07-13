package com.cognizant.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cognizant.auth.util.JwtUtil;

@Controller
@RequestMapping("/validate")
public class ValidationController {

	@Autowired
	UserDetailsService userService;
	@Autowired
	JwtUtil jwtUtil;

	/**
	 * Checks if the token is a valid admin token
	 * 
	 * @URL: http://localhost:8090/auth/validate/admin
	 * 
	 * @Header: [Authorization] = JWT Token
	 * 
	 * @param token
	 * @return
	 */
	@GetMapping("/admin")
	public ResponseEntity<Boolean> validateAdmin(@RequestHeader(name = "Authorization") String token) {

		if (!jwtUtil.isTokenExpiredOrInvalidFormat(token)) {
			UserDetails user = userService.loadUserByUsername(jwtUtil.getUsernameFromToken(token));
			if (user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))
				return new ResponseEntity<>(true, HttpStatus.OK);
			else {
				return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
			}
		} else {
			return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * Checks if the token is a valid student token and is of the correct user
	 * 
	 * @URL: http://localhost:8090/auth/validate/student/{USERNAME}
	 * 
	 * @Header: [Authorization] = JWT Token
	 * 
	 * @param token
	 * @return
	 */
	@GetMapping("/student/{username}")
	public ResponseEntity<Boolean> validateStudent(@RequestHeader(name = "Authorization") String token,
			@PathVariable(name = "username") String username) {
		
		if (jwtUtil.validateToken(token, username)) {
			UserDetails user = userService.loadUserByUsername(username);
			if (user.getAuthorities().contains(new SimpleGrantedAuthority("STUDENT"))) {
				return new ResponseEntity<>(true, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
			}
		}
		return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
	}
}
