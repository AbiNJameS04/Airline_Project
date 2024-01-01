package com.spring.hackathon.dto;

import java.util.List;

import lombok.Data;

@Data
public class MultipleSearchRequestDTO {
	
	private List<FlightSearchRequestDTO> routes;

}
