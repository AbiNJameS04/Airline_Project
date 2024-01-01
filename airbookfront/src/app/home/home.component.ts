import { Component, OnInit } from '@angular/core';
import { AirportsService } from '../airports.service';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NavigationExtras, Router } from '@angular/router';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  selectedFlightType: string = 'roundtrip';

  searchForm: FormGroup;
// from=new FormControl()
// to=new FormControl()
  multiCityFlights: any[] = [];

  fromsuges: any[] = [];
  tosuges: any[] = [];
  routes: any[] = [];
  
  constructor(
    private router: Router,
    private as: AirportsService,
    private formBuilder: FormBuilder,
    private datePipe: DatePipe
  ) {
    this.searchForm = this.formBuilder.group({
      iataFrom: ['', Validators.required],
      iataTo: ['', Validators.required],
      date: [''],
      rdate:[''],
      classType: [''],
    });
  }

  ngOnInit(): void {}
  formatDate(date: Date): string {
    // Format the date to 'yyyy/MM/dd' format
    return this.datePipe.transform(date, 'yyyy/MM/dd') || '';
  }
  minDate(): string {
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0'); // January is 0
    const day = String(today.getDate()).padStart(2, '0');

    return `${year}-${month}-${day}`;
  }
  
  onFlightTypeChange(type: string) {
    this.selectedFlightType = type;

    if (type === 'multi-city') {
      this.multiCityFlights = [{ from: '', to: '' }];
    } else {
      this.multiCityFlights = [];
    }
  }

  addFlight() {
    this.multiCityFlights.push({ from: '', to: '' });
  }

  removeFlight(index: number) {
    if (index >= 0 && index < this.multiCityFlights.length) {
      this.multiCityFlights.splice(index, 1);
    }
  }

suggestfro() {
    const searchText = this.searchForm.get('iataFrom')?.value;
    console.log(searchText)
    if (searchText) {
      this.as.getSuggestions(searchText).subscribe((data) => {
        this.fromsuges = data;
        console.log(data);
      });
    } else {
      this.fromsuges = [];
      console.log("erro")
    }
  }

  selectFro(suggestion: any)
   {
   this.searchForm.get('iataFrom')?.setValue(suggestion.iata_code); 
    this.fromsuges = [];
  }


  suggestTo()
   {
    const searchText = this.searchForm.get('iataTo')?.value;
    if (searchText) {
      this.as.getSuggestions(searchText).subscribe((data) => {
        this.tosuges = data;
        console.log(data);
      });
    } else {
      this.tosuges = [];
      console.log("error");
      
    }
  }

  selectTo(suggestion: any) {
    this.searchForm.get('iataTo')?.setValue(suggestion.iata_code); 
   this.tosuges = [];
  }


  searchF() {
    const criteria = JSON.stringify(this.searchForm.value);
    console.log(criteria); // Output the criteria to the console
  
    let routeTo;
  
    switch (this.selectedFlightType) {
      case 'one-way':
        routeTo = '/search';
        break;
      case 'roundtrip':
        routeTo = '/round';
        break;
      default:
        routeTo = '/home';
        break;
    }
   
    this.router.navigate([routeTo], {
      queryParams: { criteria }, // Pass criteria as query parameters
    });
 
  }
  }
  
  
    
   
  
  


