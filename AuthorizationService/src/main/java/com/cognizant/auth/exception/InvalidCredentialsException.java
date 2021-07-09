package com.cognizant.auth.exception;

/**
 * @author Sunmeet
 */
public class InvalidCredentialsException extends RuntimeException {
	
	private static final long serialVersionUID = 7759904955002332236L;

	public InvalidCredentialsException(String message) {
		super(message);
	}
}
