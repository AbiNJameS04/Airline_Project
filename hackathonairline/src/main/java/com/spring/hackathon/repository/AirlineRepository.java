package com.spring.hackathon.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spring.hackathon.entity.Airline;



@Repository
public interface AirlineRepository  extends MongoRepository<Airline, String>{


}
