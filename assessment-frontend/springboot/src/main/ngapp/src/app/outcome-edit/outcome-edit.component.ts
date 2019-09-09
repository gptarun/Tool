import { Component, OnInit } from '@angular/core';
import { Outcome } from '../outcome/outcome.component';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

export class OutcomeLoader{
  constructor(
    public catName:string,
    public compName:string,
    public outcomes:string[]
  ){}
}
var json = require('src/assets/outcomes.json');
@Component({
  selector: 'app-outcome-edit',
  templateUrl: './outcome-edit.component.html',
  styleUrls: ['./outcome-edit.component.css']
})
export class OutcomeEditComponent implements OnInit {

  idx:number = 0;
  constructor(private http: HttpClient) { }

  recurAddOutcome(){
    if(json[this.idx] == null){return null}
    let outcome = {
      catName: json[this.idx]["Category"],
      compName: json[this.idx]["Competency"],
      outcomes: 
      [
        json[this.idx]["1 - Limited"],
        json[this.idx]["2 - Implemented"],
        json[this.idx]["3 - Evolving"],
        json[this.idx]["4 - Integrated"],
        json[this.idx]["5 - Adaptive"]
      ]
    }
    this.idx++;
    //console.log(outcome);
    this.http.put<OutcomeLoader>(environment.apiTarget+`/outcome`, outcome).subscribe(
      res => {return this.recurAddOutcome()}
    )

  }
  ngOnInit() {
    this.recurAddOutcome();
  }

}
