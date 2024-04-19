package com.example.cms.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.cms.exception.PostIdNotFoundException;
@RestControllerAdvice
public class PostIdNotFoundExceptionHandler {
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlePostIdNotFoundException(PostIdNotFoundException ex){
		
		ErrorStructure<String> errorStrecture = new ErrorStructure<>();
		errorStrecture.setErrorCode(HttpStatus.NOT_FOUND.value());
		errorStrecture.setErrorMessage(ex.getMessage());
		errorStrecture.setRootCause("Entered Panel Id is Not Found In DataBase");
		
		return new ResponseEntity<ErrorStructure<String>>(errorStrecture,HttpStatus.NOT_FOUND);
	}
}
