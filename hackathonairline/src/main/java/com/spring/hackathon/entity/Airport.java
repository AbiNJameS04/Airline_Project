package com.spring.hackathon.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Document(collection = "airports")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Airport {

	@JsonProperty("time_zone")
	private String time_zone;
	private String name;
	private Double longitude;
	private Double latitude;
	private String id;
	@JsonProperty("icao_code")
	private String icaoCode;
	@JsonProperty("iata_country_code")
	private String iataCountryCode;
	@JsonProperty("iata_code")
	private String iataCode;
	@JsonProperty("iata_city_code")
	private String iataCityCode;
	@JsonProperty("city_name")
	private String cityName;
	@JsonProperty("city_iata_code")
	private String cityIataCode;

}
