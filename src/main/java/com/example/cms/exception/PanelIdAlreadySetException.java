package com.example.cms.exception;


public class PanelIdAlreadySetException extends RuntimeException {

	private String message;

	public PanelIdAlreadySetException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
