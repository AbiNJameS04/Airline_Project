package com.spring.hackathon.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.hackathon.dto.FlightSearchRequestDTO;
import com.spring.hackathon.entity.Flight;
import com.spring.hackathon.exceptions.ResourceNotFoundException;
import com.spring.hackathon.repository.FlightRepository;

@Service
public class MultiCitySearch {
	private final FlightRepository flightRepository;

	@Autowired
	public MultiCitySearch(FlightRepository flightRepository) {
		this.flightRepository = flightRepository;
	}
	
	

	public List<List<Flight>> findFlightsByList(List<FlightSearchRequestDTO> routes) throws ResourceNotFoundException {

		List<List<Flight>> allFlights = new ArrayList<>();
		
		for(FlightSearchRequestDTO route:routes) {
			String iataFrom = route.getIataFrom();
			String iataTo = route.getIataTo();
			String date = route.getDate();
//			String classType = route.getClassType();
			
			String dateToDay = mapDateToDay(date);
			
			List<Flight> flightList = flightRepository.findByIataFromAndIataTo(iataFrom, iataTo, dateToDay);
			
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
