package com.example.cms.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class IllegalAccessRequestExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleIllegalAccessRequestException(com.example.cms.exception.IllegalAccessRequestException ex){
		
		ErrorStructure<String> errorStrecture = new ErrorStructure<>();
		errorStrecture.setErrorCode(HttpStatus.NOT_FOUND.value());
		errorStrecture.setErrorMessage(ex.getMessage());
		errorStrecture.setRootCause("First Login Your Account and Do Your Operation");
		
		return new ResponseEntity<ErrorStructure<String>>(errorStrecture,HttpStatus.NOT_FOUND);
	}
}
