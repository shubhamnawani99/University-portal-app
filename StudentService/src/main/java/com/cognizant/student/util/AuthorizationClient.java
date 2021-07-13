package com.cognizant.student.util;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth", url = "http://localhost:8090/auth/validate")
public interface AuthorizationClient {

	@GetMapping("/student/{username}")
	public ResponseEntity<Boolean> validateStudent(@RequestHeader(name = "Authorization") String token,
			@PathVariable(name = "username") String username);

}
