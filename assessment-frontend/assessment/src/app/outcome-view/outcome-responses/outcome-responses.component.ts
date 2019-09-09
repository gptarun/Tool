import { Component, OnInit, Input } from '@angular/core';
import { IndivOutcome } from '../../service/data/outcomes.service';
import { Answer } from '../../question/question.component';
import { OutcomeViewComponent } from '../outcome-view.component';

@Component({
  selector: 'app-outcome-responses',
  templateUrl: './outcome-responses.component.html',
  styleUrls: ['./outcome-responses.component.css', '../outcome-view.component.css'],
  providers: [{provide: OutcomeViewComponent, useExisting: OutcomeResponsesComponent}]
})
export class OutcomeResponsesComponent extends OutcomeViewComponent implements OnInit {

  @Input() outcomesByCategory:IndivOutcome[][];
  @Input() answersByCategory:Answer[][];
  @Input() sessionHistory:any[];
  constructor() { 
    super();
    
  }

  ngOnInit() {
  }

}
