package com.example.cms.exception;

public class PostIdNotFoundException extends RuntimeException {

	private String message;
	public PostIdNotFoundException(String message) {
		super();
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
}
