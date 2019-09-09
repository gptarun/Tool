import { Injectable } from '@angular/core';
import { Question } from '../../question/question.component';
import { HttpClient } from '../../../../node_modules/@angular/common/http';
import { environment } from '../../../environments/environment';


// tslint:disable-next-line: comment-format
//Hits endpoints in QuestionResource in com.assessment.assessment.question
@Injectable({
  providedIn: 'root'
})
export class QuestionsService {

  questions: Question[];

  constructor(private http: HttpClient) {

  }


  getQuestion(id) {

    return this.http.get<Question>(environment.apiTarget + `/question/${id}`);
  }
  getAllQuestions() {
    return this.http.get<Question[]>(environment.apiTarget + '/question');
  }
  checkIfMoreQuestions(id, category) {
    return this.http.get<Question>(environment.apiTarget + `/question/${id}/${category}`)
  }
  getPrevQuestion(id, category) {
    return this.http.get<Question>(environment.apiTarget + `/question/${id}/${category}/prev`)
  }
}
