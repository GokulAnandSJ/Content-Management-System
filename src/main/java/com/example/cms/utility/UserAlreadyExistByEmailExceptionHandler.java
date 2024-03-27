package com.example.cms.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.cms.exception.UserAlreadyExistByEmailException;

@RestControllerAdvice
public class UserAlreadyExistByEmailExceptionHandler {

	private ErrorStructure<String> errorStructure;

	public UserAlreadyExistByEmailExceptionHandler(ErrorStructure<String> errorStructure) {
		super();
		this.errorStructure = errorStructure;
	}

	private ResponseEntity<ErrorStructure<String>> errorResponse(HttpStatus status, String message, String rootCause) {
		return new ResponseEntity<ErrorStructure<String>>(errorStructure.setErrorCode(status.value())
				.setErrorMessage(message)
				.setRootCause(rootCause),status);
	}
				

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleUserAlreadyExistByEmail(UserAlreadyExistByEmailException ex) {

		return errorResponse(HttpStatus.BAD_REQUEST, "Entered email is already Existing please use Another Email", ex.getMessage());

	}
	
}
