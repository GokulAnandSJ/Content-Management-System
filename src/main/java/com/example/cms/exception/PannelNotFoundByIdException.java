package com.example.cms.exception;

public class PannelNotFoundByIdException extends RuntimeException{
	private String message;

	public PannelNotFoundByIdException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
