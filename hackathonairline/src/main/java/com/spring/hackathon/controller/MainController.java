package com.spring.hackathon.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.hackathon.dto.FlightSearchDTO;
import com.spring.hackathon.dto.FlightSearchRequestDTO;
//import com.spring.hackathon.dto.MultipleSearchRequestDTO;
import com.spring.hackathon.entity.Airline;
import com.spring.hackathon.entity.Airport;
import com.spring.hackathon.entity.Flight;
import com.spring.hackathon.exceptions.ResourceNotFoundException;
import com.spring.hackathon.exceptions.TodayNoFlightRunningOnThisRoute;
import com.spring.hackathon.repository.SearchRepository;
import com.spring.hackathon.service.FlightSearchService;
import com.spring.hackathon.service.MainService;
import com.spring.hackathon.service.MultiCitySearch;
import com.spring.hackathon.service.RouteSearchByIdService;
import com.spring.hackathon.service.RouteSearchService;


@RestController
@RequestMapping("/api/v1/flights")
// @CrossOrigin("*")
public class MainController {

	@Autowired
	private MainService mainService;

	@Autowired
	private FlightSearchService flightSearchService;
		
	@Autowired
	private MultiCitySearch multiCitySearch;
	
	@Autowired
	private RouteSearchService rs;
	
	@Autowired
	private RouteSearchByIdService rIdService;

	@Autowired
	private SearchRepository searchRepository;
	
	
	@PostMapping("/readAirline")
	public void readAndSaveAirline(@RequestParam String filePath) throws IOException {
		mainService.readJsonFileAndSaveToMongo(filePath);
	}

	@PostMapping("/readAirport")
	public ResponseEntity<String> readAndSaveAirport(@RequestParam String filePath) throws IOException {
		mainService.readAirPortFileAndSaveToMongo(filePath);
		return new ResponseEntity<>("Data Added...", HttpStatus.OK);
	}

	// triggering this method when this end-point will hit from postman
	@PostMapping("/importFlights")
	public void importFlights() {
		mainService.importFlightsFromJsonFile();
	}

	// fetching all airports
	@GetMapping("/fetchAirports")
	public ResponseEntity<List<Airport>> readAllAirport() {
		List<Airport> allAirports = mainService.readAllAirports();
		return new ResponseEntity<List<Airport>>(allAirports, HttpStatus.OK);
	}

	// fetching airports by iata code
	@GetMapping("/iatacode/{iataCode}")
	public ResponseEntity<List<Airport>> readAirportByIataCode(@PathVariable String iataCode) {

		List<Airport> code = mainService.readAirportByIataCode(iataCode);

		return new ResponseEntity<>(code, HttpStatus.OK);
	}

	// fetching airline by iata code
	@GetMapping("/fetchAirline/{code}")
	public ResponseEntity<Airline> fetchAirlineByIataCode(@PathVariable String code) {
		Airline airline = mainService.readAirlineByIataCode(code);

		return new ResponseEntity<>(airline, HttpStatus.OK);
	}

	// fetching all airline data
	@GetMapping("/fetchAllAirline")
	public ResponseEntity<List<Airline>> fetchAllAirline() {
		List<Airline> readAllAirline = mainService.readAllAirline();
		return new ResponseEntity<>(readAllAirline, HttpStatus.OK);
	}

	// searching airlines between the two routes by single query
	@PostMapping("/searchQuery")
	public ResponseEntity<List<Flight>> searchFlights(@RequestBody FlightSearchRequestDTO request)
			throws ResourceNotFoundException, TodayNoFlightRunningOnThisRoute {
		List<Flight> filteredFlights = flightSearchService.findFlights(request);

		return ResponseEntity.ok(filteredFlights);
	}
	
	
	@PostMapping("/searchFlights")
	public ResponseEntity<List<List<Flight>>> findMultiFlights(@RequestBody List<FlightSearchRequestDTO> routes) throws ResourceNotFoundException {
		
		List<List<Flight>> flightsByList = multiCitySearch.findFlightsByList(routes);
		System.out.println(flightsByList);
		
		return new ResponseEntity<>(flightsByList, HttpStatus.OK);
	}
	
	@PostMapping("/searchRoutesList")
    public ResponseEntity<List<List<Flight>>> searchMultiFlights(@RequestBody FlightSearchDTO requestDTO) {
        try {
            List<List<Flight>> allFlights = rs.findFlights(requestDTO.getRoutes());
            return new ResponseEntity<>(allFlights, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            // Handle exceptions and return an appropriate HTTP response
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	@PostMapping("/searchFlightsByRouteIds")
    public ResponseEntity<List<List<Flight>>> searchFlightsByRouteIds(@RequestBody List<Integer> routeIds) throws TodayNoFlightRunningOnThisRoute {
        try {
            List<List<Flight>> allFlights = rIdService.findFlights(routeIds);
            return new ResponseEntity<>(allFlights, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


	///sugessions for airport
	@GetMapping("/sugesstions/{text}")
	public List<Airport> sugesstions(@PathVariable String text) {
		return searchRepository.findByText(text);
	}

	@GetMapping("/getFlights")
	public ResponseEntity<List<Flight>> fetchFlights(){
		List<Flight> readFlights = mainService.readFlights();

		return ResponseEntity.ok(readFlights);

	}
	
	
	

}
