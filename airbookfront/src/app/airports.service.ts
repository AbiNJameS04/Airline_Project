import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { FlightReaq } from './flight-reaq';
import { Flight } from './flight';

@Injectable({
  providedIn: 'root'
})
export class AirportsService {
  URL='http://localhost:8081/api/v1/flights'

  private apiUrl = 'http://localhost:8081/api/v1/flights/sugesstions/';
  queryParams: any;
  

  constructor(private http:HttpClient) { }

  fnGetAllairports()
  {
    return this.http.get(this.URL+"/fetchAirports");
  }

  getAirlineByIataCode(iataCode: string): Observable<any> {
    const url = `${this.URL}/fetchAirline/${iataCode}`;
    return this.http.get(url);
  }

  getAirportByIataCode(iataCode: string): Observable<any> {
    const url = `${this.URL}/iatacode/${iataCode}`;
    return this.http.get(url);
  }


  getSuggestions(text: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}${text}`);
  }
search(criteria: any): Observable<any[]> {
    const url = `${this.URL}/searchQuery`;
    
    // Set the headers to specify JSON content type
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    // Send the POST request with headers
    return this.http.post<any[]>(url, criteria, { headers });
  }

  roundsearch(criteria: any): Observable<any[]> {
    const url = `${this.URL}/searchRoutesList`;
    
    // Set the headers to specify JSON content type
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    // Send the POST request with headers
    return this.http.post<any[]>(url, criteria, { headers });
  }
  
  
}
