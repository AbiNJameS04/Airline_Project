package com.spring.hackathon.dto;

import java.util.List;

import com.spring.hackathon.entity.Route;

import lombok.Data;


@Data
public class FlightSearchDTO {
	
	private List<Route> routes;

}
