package com.example.cms.exception;

public class UserIdIsNotFound extends RuntimeException {
	
	private String message;

	public UserIdIsNotFound(String message) {
		super();
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
}
