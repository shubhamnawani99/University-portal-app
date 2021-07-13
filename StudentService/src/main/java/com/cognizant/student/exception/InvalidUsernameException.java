package com.cognizant.student.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvalidUsernameException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6613560110315119146L;

	public InvalidUsernameException(String msg) {
		super(msg);
		log.error(msg);
	}
}
