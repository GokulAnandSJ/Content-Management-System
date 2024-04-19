package com.example.cms.exception;

public class IllegalAccessRequestException extends RuntimeException {

	private String message;

	public IllegalAccessRequestException(String message) {
		
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
}
