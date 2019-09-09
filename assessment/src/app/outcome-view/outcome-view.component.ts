import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-outcome-view',
  templateUrl: './outcome-view.component.html',
  styleUrls: ['./outcome-view.component.css']
})
export class OutcomeViewComponent implements OnInit {

  active:boolean = false;
  catColors:any = 
  {
    'Talent':'rgba(0, 70, 127, .75)',
    'Organization':'rgba(250, 175, 0, .75)',
    'Flow':'rgba(0, 185, 137, .75)',
    'Technical':'rgba(77, 180, 250, .75)',
    'Governance':'rgba(157, 166, 171, .75)'
  }
  constructor() { }

  ngOnInit() {
  }

}
