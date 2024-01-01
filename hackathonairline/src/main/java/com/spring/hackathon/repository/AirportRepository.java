package com.spring.hackathon.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spring.hackathon.entity.Airport;


@Repository
public interface AirportRepository  extends MongoRepository<Airport, String>{
	
	
	List<Airport> findByIataCode(String iataCode);
}
