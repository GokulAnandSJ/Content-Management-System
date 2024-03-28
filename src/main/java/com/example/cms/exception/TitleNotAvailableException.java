package com.example.cms.exception;

public class TitleNotAvailableException extends RuntimeException {

	private String message;
	
	public TitleNotAvailableException(String message){
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
