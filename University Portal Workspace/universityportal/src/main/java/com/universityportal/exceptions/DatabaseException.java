package com.universityportal.exceptions;

public class DatabaseException extends Exception {

	private static final long serialVersionUID = 5097665761244310051L;

	private final int code;
	
	public DatabaseException(String message, int code) {
		super(message);
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
