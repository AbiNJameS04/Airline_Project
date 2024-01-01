package com.spring.hackathon.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Document(collection = "airlines")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Airline {

	private String name;
	@Id
	private String code;
	@JsonProperty("is_lowcost")
	private String isLowcost;
	private String logo;

}
