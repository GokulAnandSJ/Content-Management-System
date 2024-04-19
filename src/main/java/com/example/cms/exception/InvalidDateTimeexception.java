package com.example.cms.exception;

public class InvalidDateTimeexception extends RuntimeException {

	private String message;

	public InvalidDateTimeexception(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
