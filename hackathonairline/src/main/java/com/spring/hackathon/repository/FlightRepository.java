package com.spring.hackathon.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spring.hackathon.entity.Flight;

@Repository
public interface FlightRepository extends MongoRepository<Flight, Integer> {
	
	List<Flight> findByIataFromAndIataTo(String iataFrom, String iataTo, String day);
	
    List<Flight> findByIataFromAndIataTo(String iataFrom, String iataTo, String day, String classType);
    
    List<Flight> findByRouteId(Integer routeId);

}
