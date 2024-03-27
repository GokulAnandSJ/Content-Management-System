package com.example.cms.exception;

public class UserNotFoundById extends RuntimeException {

	private String message;

	public UserNotFoundById(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
}
