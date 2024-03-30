package com.example.cms.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.cms.exception.UserNotFoundById;


@RestControllerAdvice
public class UserIdNotFoundExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> userIdNotFound(UserNotFoundById exc){
		
		ErrorStructure<String> errorStrecture = new ErrorStructure<>();
		errorStrecture.setErrorCode(HttpStatus.NOT_FOUND.value());
		errorStrecture.setErrorMessage(exc.getMessage());
		errorStrecture.setRootCause("Entered User Id is Not Found In DataBase");
		
		return new ResponseEntity<ErrorStructure<String>>(errorStrecture,HttpStatus.NOT_FOUND);
	}
}
