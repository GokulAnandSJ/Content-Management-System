package com.example.cms.utility;

import org.springframework.stereotype.Component;

@Component
public class ErrorStructure<G> {
	private int errorCode;
	private String errorMessage;
	private G rootCause;
	
	
	public int getErrorCode() {
		return errorCode;
	}
	public ErrorStructure<G> setErrorCode(int errorCode) {
		this.errorCode = errorCode;
		return this;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public ErrorStructure<G> setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		return this;
	}
	public G getRootCause() {
		return rootCause;
	}
	public ErrorStructure<G> setRootCause(G rootCause) {
		this.rootCause = rootCause;
		return this;
	}
		
}
