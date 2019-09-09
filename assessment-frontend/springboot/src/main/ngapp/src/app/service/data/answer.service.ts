import { Injectable } from '@angular/core';
import { Answer } from '../../question/question.component';
import { HttpClient, HttpHeaders } from '../../../../node_modules/@angular/common/http';
import { environment } from '../../../environments/environment';



@Injectable({
  providedIn: 'root'
})
//hits endpoints in AnswerResource under com.assessment.assessment.answer in the backend

export class AnswerService {

  answers:Object = {}
  constructor(private http:HttpClient) { 
    
  }

  storeAnswer(id, choice,  dbId){ 
    let answer = new Answer(id, choice, null, null);
    return this.http.put<Answer>(environment.apiTarget+`/${dbId}/answer`, answer)
  }

  getAnswers(urlKey){
    return this.http.get<Answer>(environment.apiTarget+`/${urlKey}/answer`)
  }

  getAnswersHistory(urlKey){
    return this.http.get<Answer>(environment.apiTarget+`/${urlKey}/answer/history`)
  }
}
