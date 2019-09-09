import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';


//Hits endpoint at DemographicService in com.assessment.assessment.demographic
@Injectable({
  providedIn: 'root'
})
export class DemographicsService {

  constructor(private http:HttpClient) { }

  getPortfolios(){
    return this.http.get<any>(environment.apiTarget+'/portfolio');
  }
}
