package com.spring.hackathon.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spring.hackathon.payload.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handlerResourceNotFoundException(ResourceNotFoundException ex) {
		
		String message = ex.getMessage();
		ErrorResponse errorResponse = ErrorResponse
										.builder().message(message).success(true).status(HttpStatus.NOT_FOUND).build();
		
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(TodayNoFlightRunningOnThisRoute.class)
	public ResponseEntity<ErrorResponse> handlerTodayNoFlightRunningOnThisRoute(TodayNoFlightRunningOnThisRoute ex) {
		
		String message = ex.getMessage();
		ErrorResponse errorResponse = ErrorResponse
										.builder().message(message).success(true).status(HttpStatus.NOT_FOUND).build();
		
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
	}

}
