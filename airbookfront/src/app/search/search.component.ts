import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AirportsService } from '../airports.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  
  
 
  airportDataFrom: any;
  airportDataTo: any;
  airports:any;
 to:any
  routes: any[] = [];
  criteria: any;
  airlineDetails: any;

  constructor(private ar: ActivatedRoute, private as: AirportsService) { }

  ngOnInit(): void {

    this.ar.queryParams.subscribe((queryParams) => {
      this.criteria = JSON.parse(queryParams.criteria || '{}');
      console.log('Received criteria:', this.criteria);
      // Now you can use this.criteria in your component as needed
    });
  console.log("FROM",this.criteria.iataFrom)
  this.getAirportsFrom(this.criteria.iataFrom)
  this.getAirportsTo(this.criteria.iataTo)
  this.sendDataToBackend(this.criteria);
}
getAirportsFrom(data:any)
{
  this.as.getAirportByIataCode(data).subscribe((response)=>{
  this.airportDataFrom=response
console.log(this.airportDataFrom)
if (this.airportDataFrom && this.airportDataFrom.length > 0) {
  this.airportDataFrom.forEach((airport:any) => {
     this.airports = airport.city_name; // Replace 'airlineIata' with the actual property name in your flight data
    console.log(this.airports)
    });}
},
  (error) => {
    console.error('Error fetching airline details:', error);
  });
}
getAirportsTo(data:string)
{
  this.as.getAirportByIataCode(data).subscribe((response)=>{
  this.airportDataTo=response
  if (this.airportDataTo && this.airportDataTo.length > 0) {
    this.airportDataTo.forEach((airport:any) => {
       this.to = airport.city_name; 
     
      });}},
  (error) => {
    console.error('Error fetching airline details:', error);
  });
}
  sendDataToBackend(data: any) {
    this.as.search(data).subscribe((response) => {
      console.log('API Response', response);
      this.routes = response;
      if (this.routes && this.routes.length > 0) {
        this.routes.forEach((flight) => {
          const airlineIATACode = flight.airlineIata; // Replace 'airlineIata' with the actual property name in your flight data
          console.log(airlineIATACode)
          this.fetchAirlineDetails(airlineIATACode);
        });
      }
    });
  }

  fetchAirlineDetails(airlineIATACode: string) {
    
    this.as.getAirlineByIataCode(airlineIATACode).subscribe(
      (data) => {
        this.airlineDetails = data;
        // Process airline details as needed
        console.log("data",data);
      },
      (error) => {
        console.error('Error fetching airline details:', error);
      }
    );
  }


}
