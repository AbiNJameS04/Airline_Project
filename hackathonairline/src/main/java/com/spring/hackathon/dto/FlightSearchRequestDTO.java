package com.spring.hackathon.dto;


import lombok.Data;


@Data
public class FlightSearchRequestDTO {
	
	private String iataFrom;
	private String iataTo;
	private String date;
	private String classType;

}
