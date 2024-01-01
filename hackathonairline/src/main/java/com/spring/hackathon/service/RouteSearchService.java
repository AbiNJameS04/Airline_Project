package com.spring.hackathon.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.hackathon.entity.Flight;
import com.spring.hackathon.entity.Route;
import com.spring.hackathon.exceptions.ResourceNotFoundException;
import com.spring.hackathon.repository.FlightRepository;

@Service
public class RouteSearchService {

	private final FlightRepository fr;

	@Autowired
	public RouteSearchService(FlightRepository flightRepository) {
		this.fr = flightRepository;
	}

	public List<List<Flight>> findFlights(List<Route> routes) throws ResourceNotFoundException {

		List<List<Flight>> allFlights = new ArrayList<>();

		for (Route route : routes) {
			
			System.out.println(route);
			
			String iataFrom = route.getIataFrom();
			String iataTo = route.getIataTo();
			String departureDate = route.getDate();

			String day = mapDateToDay(departureDate);
			List<Flight> flightList = fr.findByIataFromAndIataTo(iataFrom, iataTo, departureDate);

            if (flightList.isEmpty()) {
                throw new ResourceNotFoundException("Not found on specified route.");
            } else {
                allFlights.add(flightList);
            }
        }

        return allFlights;

	}

	private String mapDateToDay(String departureDate) {

		try {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date parse = sdf.parse(departureDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(parse);

			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

			return "day" + (dayOfWeek - 1);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
