import { Component, OnInit, Input } from '@angular/core';
import { IndivOutcome } from 'src/app/service/data/outcomes.service';
import { OutcomeViewComponent } from '../outcome-view.component';
import { Answer } from 'src/app/question/question.component';

@Component({
  selector: 'app-outcome-learning-catalog',
  templateUrl: './outcome-learning-catalog.component.html',
  styleUrls: ['./outcome-learning-catalog.component.css', '../outcome-view.component.css']
})
export class OutcomeLearningCatalogComponent extends OutcomeViewComponent implements OnInit {

  @Input() outcomesByCategory:IndivOutcome[][];
  @Input() answersByCategory:Answer[][];
  @Input() sessionHistory:any[];

  constructor() { 
    super();
  }

  ngOnInit() {
   
  }

  ifOutcomes(idx){
    let outcomeExists = false;
    this.outcomesByCategory[idx].forEach((o)=>{
        if(o.level != 0) outcomeExists = true;
    })
    return outcomeExists;
  }
}
