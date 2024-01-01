package com.spring.hackathon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.hackathon.entity.Flight;
import com.spring.hackathon.exceptions.ResourceNotFoundException;
import com.spring.hackathon.exceptions.TodayNoFlightRunningOnThisRoute;
import com.spring.hackathon.repository.FlightRepository;

@Service
public class RouteSearchByIdService {

	private final FlightRepository fr;

	@Autowired
	public RouteSearchByIdService(FlightRepository flightRepository) {
		this.fr = flightRepository;
	}

	public List<List<Flight>> findFlights(List<Integer> routeIds)
            throws ResourceNotFoundException, TodayNoFlightRunningOnThisRoute {
        List<List<Flight>> allFlights = new ArrayList<>();

        for (Integer routeId : routeIds) {
            List<Flight> flightList = fr.findByRouteId(routeId);

            if (flightList.isEmpty()) {
                throw new ResourceNotFoundException("Not found on specified route.");
            } else {
                allFlights.add(flightList);
            }
        }

        return allFlights;
    }

}
