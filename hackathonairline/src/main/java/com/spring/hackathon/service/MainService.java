package com.spring.hackathon.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.hackathon.entity.Airline;
import com.spring.hackathon.entity.Airport;
import com.spring.hackathon.entity.Flight;
import com.spring.hackathon.repository.AirlineRepository;
import com.spring.hackathon.repository.AirportRepository;
import com.spring.hackathon.repository.FlightRepository;

@Service
public class MainService {

	@Autowired
	private AirlineRepository airlineRepository;

	@Autowired
	private AirportRepository airportRepository;

	@Autowired
	private FlightRepository flightRepository;

	private ResourceLoader resourceLoader;

	@Autowired
	public MainService(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	// Reading Airline JSON File
	public void readJsonFileAndSaveToMongo(String filePath) throws IOException {
		// Load the JSON file as a Resource
		Resource resource = resourceLoader.getResource("classpath:airline-data/airlines-data.json");

		// Read the JSON content from the Resource
		String jsonContent = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

		// Parse the JSON content into a list of YourModel objects
		ObjectMapper objectMapper = new ObjectMapper();
		List<Airline> airlineModel = objectMapper.readValue(jsonContent,
				objectMapper.getTypeFactory().constructCollectionType(List.class, Airline.class));

		System.out.println(airlineModel);
		// Save the parsed data to MongoDB
		airlineRepository.saveAll(airlineModel);

	}

	// reading airport JSON file
	public void readAirPortFileAndSaveToMongo(String filePath) throws IOException {
		// Load the JSON file as a Resource
		Resource resource = resourceLoader.getResource("classpath:airline-data/airports-data.json");

		// Read the JSON content from the Resource
		String jsonContent = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

		// Parse the JSON content into a list of YourModel objects
		ObjectMapper objectMapper = new ObjectMapper();
		List<Airport> airlineModel = objectMapper.readValue(jsonContent,
				objectMapper.getTypeFactory().constructCollectionType(List.class, Airport.class));

		System.out.println(airlineModel);
		// Save the parsed data to MongoDB
		airportRepository.saveAll(airlineModel);

	}

	// reading flight routes json file
	public void importFlightsFromJsonFile() {
		try {
			// Loading the JSON file
			File jsonFile = ResourceUtils.getFile("classpath:airline-data/flightroutes-data.json");

			// Initializing ObjectMapper to de-serialize JSON
			ObjectMapper objectMapper = new ObjectMapper();

			// Read JSON data into a list of Flight objects
			List<Flight> flights = objectMapper.readValue(jsonFile, new TypeReference<List<Flight>>() {
			});

			// Assign routeId to each flight based on your logic
			for (Flight flight : flights) {
				// Your route identification logic here
				String iataFrom = flight.getIataFrom();
				String iataTo = flight.getIataTo();

				// Example: Concatenate departure and destination airports as a route key
				String routeKey = iataFrom + "-" + iataTo;

				// Calculate the hash code and use its absolute value
				int routeId = Math.abs(routeKey.hashCode());

				flight.setRouteId(routeId);
			}

			// Save the list of Flight objects to MongoDB
			flightRepository.saveAll(flights);

			System.out.println("Imported " + flights.size() + " flights from JSON file to MongoDB.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// reading all airports
	public List<Airport> readAllAirports() {
		return airportRepository.findAll();
	}

	// reading airport by iata code
	public List<Airport> readAirportByIataCode(String iataCode) {

		List<Airport> iata_code = airportRepository.findByIataCode(iataCode);
		return iata_code;

	}

	// reading airline by iata code
	public Airline readAirlineByIataCode(String code) {
		Optional<Airline> temp = airlineRepository.findById(code);
		Airline airline = null;
		if (temp.isPresent()) {
			airline = temp.get();
		}
		return airline;
	}

	public List<Airline> readAllAirline() {
		return airlineRepository.findAll();
	}

	public List<Flight> readFlights(){
		return flightRepository.findAll();

	}
}
