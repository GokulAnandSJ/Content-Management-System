package com.example.cms.exception;

public class UserHavingPanelException extends RuntimeException {

	private String message;

	public UserHavingPanelException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
