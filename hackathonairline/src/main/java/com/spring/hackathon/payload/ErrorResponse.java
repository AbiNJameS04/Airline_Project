package com.spring.hackathon.payload;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
	
	private String message;
	private Boolean success;
	private HttpStatus status; 

}
