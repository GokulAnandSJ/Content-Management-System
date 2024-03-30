package com.example.cms.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.cms.exception.PannelNotFoundByIdException;

@RestControllerAdvice
public class ErrorPanelIdNotFoundException {
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> PanelIdNotFoundException(PannelNotFoundByIdException ex){
		
		ErrorStructure<String> errorStrecture = new ErrorStructure<>();
		errorStrecture.setErrorCode(HttpStatus.NOT_FOUND.value());
		errorStrecture.setErrorMessage(ex.getMessage());
		errorStrecture.setRootCause("Entered Panel Id is Not Found In DataBase");
		
		return new ResponseEntity<ErrorStructure<String>>(errorStrecture,HttpStatus.NOT_FOUND);
	}
	
}
