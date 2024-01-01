package com.spring.hackathon.dto;

import java.util.List;

import com.spring.hackathon.entity.Airline;

import lombok.Data;

@Data
public class FlightResponseDTO {
	
	private List<Airline> airlines;

}
