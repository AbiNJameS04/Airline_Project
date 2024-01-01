package com.spring.hackathon.dto;

import java.util.List;

import com.spring.hackathon.entity.RoutesId;

import lombok.Data;

@Data
public class RouteSearchDTO {
	
	private List<RoutesId> routesIds;

}
