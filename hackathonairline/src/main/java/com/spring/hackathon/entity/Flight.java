package com.spring.hackathon.entity;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Document(collection = "flight-routes") // MongoDB collection name
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Flight {

	@Id
	private Integer id;
	
	
	private Integer routeId;

	@JsonProperty("iata_from")
	private String iataFrom;

	@JsonProperty("iata_to")
	private String iataTo;

	private String airlineIata;

	@JsonProperty("airline") // Use this annotation to specify the JSON field name
	private void unpackNestedIata(Map<String, Object> airline) {
		this.airlineIata = (String) airline.get("IATA");
	}

	

	@JsonProperty("is_active")
	private Integer active;

	@JsonProperty("class_business")
	private Boolean classBusiness;

	@JsonProperty("class_economy")
	private Boolean classEconomy;

	@JsonProperty("class_first")
	private Boolean classFirst;

	private String day1;

	private String day2;

	private String day3;

	private String day4;

	private String day5;

	private String day6;

	private String day7;

	@JsonProperty("flights_per_day")
	private String flightsPerDay;

	@JsonProperty("flights_per_week")
	private Integer flightsPerWeek;

	@JsonProperty("max_duration")
	private int maxDuration;

	@JsonProperty("min_duration")
	private int minDuration;

}
