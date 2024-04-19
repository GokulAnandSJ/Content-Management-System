package com.example.cms.exception;

public class TopicNotSpecifiedException extends RuntimeException {

	private String message;
	
	public TopicNotSpecifiedException(String message){
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
