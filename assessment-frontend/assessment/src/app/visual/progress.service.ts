import { Injectable } from '@angular/core';
import { EventEmitter } from '../../../node_modules/protractor';
import { BehaviorSubject } from '../../../node_modules/rxjs';
import { QuestionsService } from '../service/data/questions.service';

@Injectable({
  providedIn: 'root'
})
export class ProgressService {
  progress:number = 3;
  progressMod:number;
  progressUpdate:BehaviorSubject<number> = new BehaviorSubject<number>(0); 
  progItem = this.progressUpdate.asObservable(); //Using observables in the inc/dec progress makes it actually update visually
  constructor(private qService: QuestionsService) { 
    // this.qService.getAllQuestions().subscribe( //Determines how much to increment the progress bar by how many questions are on the assessment
    //   res=>{
    //     this.progressMod = 100/res.length;
    //     this.progress = 100/res.length;
    //   }
    // )
  }

  incProgress(){
    this.progress+=this.progressMod;
    this.progressUpdate.next(this.progress);
  }

  decProgress(){
    this.progress-=this.progressMod;
    this.progressUpdate.next(this.progress);
  }
}
