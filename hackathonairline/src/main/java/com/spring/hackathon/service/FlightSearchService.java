 package com.spring.hackathon.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.hackathon.dto.FlightSearchRequestDTO;
import com.spring.hackathon.entity.Flight;
import com.spring.hackathon.exceptions.ResourceNotFoundException;
import com.spring.hackathon.exceptions.TodayNoFlightRunningOnThisRoute;
import com.spring.hackathon.repository.FlightRepository;

@Service
public class FlightSearchService {
	private final FlightRepository flightRepository;

	@Autowired
	public FlightSearchService(FlightRepository flightRepository) {
		this.flightRepository = flightRepository;
	}

	public List<Flight> findFlights(FlightSearchRequestDTO flightSearchRequestDTO)
			throws ResourceNotFoundException, TodayNoFlightRunningOnThisRoute {
		String iataFrom = flightSearchRequestDTO.getIataFrom();
		String iataTo = flightSearchRequestDTO.getIataTo();
		String departureDate = flightSearchRequestDTO.getDate();
		String flyingclassType = flightSearchRequestDTO.getClassType();

		String day = mapDateToDay(departureDate);
		

//		boolean mapFlyingClassType = mapFlyingClassType(flyingclassType);
//
			List<Flight> flightList = flightRepository.findByIataFromAndIataTo(iataFrom, iataTo, departureDate,
					flyingclassType);
			if (flightList.isEmpty()) {
				throw new ResourceNotFoundException("Not found on specified route.");
			} else {
				return flightList;
			}


	}

//	private boolean mapFlyingClassType(String flyingclassType) {
//
//		boolean classInstanceField;
//
//		switch (flyingclassType) {
//		case "business":
//			classInstanceField = true;
//			break;
//		case "economy":
//			classInstanceField = true;
//			break;
//		case "first":
//			classInstanceField = true;
//			break;
//		default:
//			throw new IllegalArgumentException("Unexpected value: " + flyingclassType);
//		}
//
//		return classInstanceField;
//	}
	

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