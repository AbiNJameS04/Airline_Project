import { Component, OnInit } from '@angular/core';
import { AirportsService } from '../airports.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-round',
  templateUrl: './round.component.html',
  styleUrls: ['./round.component.css']
})
export class RoundComponent implements OnInit {
  criteria: any;
  routes:any;
  airroutes: any[] = [];
  airportDataFrom: any;
  airportDataTo: any;
  airports:any;
  to:any;
  flightroutes:any
  airlineDetails: any;
  constructor(private as:AirportsService,private ar: ActivatedRoute) { }

  ngOnInit(): void {

    this.ar.queryParams.subscribe((queryParams) => {
      this.criteria = JSON.parse(queryParams.criteria);
      console.log('Received criteria:', this.criteria);
      this.routes ={
        "routes" : [
        {
          "date": this.criteria.date,
          "iataFrom": this.criteria.iataFrom,
          "iataTo": this.criteria.iataTo
        },
        {
          "date": this.criteria.rdate,
          "iataFrom": this.criteria.iataTo, // Swap the from and to for the return trip
          "iataTo": this.criteria.iataFrom
        }
      ]};
      console.log("routes",this.routes)
      this.getAirportsFrom(this.criteria.iataFrom)
      this.getAirportsTo(this.criteria.iataTo)
      this.searchRoutes(this.routes);
    });


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
         console.log(this.to)
       
        });}},
    (error) => {
      console.error('Error fetching airline details:', error);
    });
  }

  searchRoutes(data:any) {
    this.as.roundsearch(data)
      .subscribe(response => {
        // Handle the response from your Spring Boot API here
        this.airroutes=response
        console.log('Response from API:', this.airroutes);
        if (this.airroutes && this.airroutes.length > 0) {
          this.flightroutes = [];
        
          this.airroutes.forEach((flight) => {
            console.log("routessss", flight);
        
            flight.forEach((element: any) => {
              console.log("code", element.airlineIata);
        
              this.flightroutes.push(element);
        
              this.fetchAirlineDetails(element.airlineIata);
            });
          });
        }
        
      }, error => {
        console.error('Error:', error);
      });
  }

  fetchAirlineDetails(airlineIATACode: string) {
    
    this.as.getAirlineByIataCode(airlineIATACode).subscribe(
      (data) => {
        this.airlineDetails = data;
        // Process airline details as needed
        console.log("data",data.name);
      },
      (error) => {
        console.error('Error fetching airline details:', error);
      }
    );
  }
}
