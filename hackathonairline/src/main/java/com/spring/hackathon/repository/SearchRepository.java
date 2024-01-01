package com.spring.hackathon.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.spring.hackathon.entity.Airport;

@Repository
public interface SearchRepository {
    
    List<Airport> findByText(String text);
    
}
