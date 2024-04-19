package com.example.cms.exception;

public class BlogIdNotFoundException extends RuntimeException{

	private String message;
	
	public BlogIdNotFoundException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
