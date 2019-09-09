import { Injectable } from '@angular/core';
import { Router } from '../../../node_modules/@angular/router';
import { HttpClient } from '../../../node_modules/@angular/common/http';
import { environment } from '../../environments/environment';
import { from } from 'rxjs';

export class Session{
  constructor(
   public sessionID:string,
    public url:string,
    public dbId:number
  ){

  }
}

//Maintains cookies and sessions. Hits endpoints at SessionResource in com.assessment.assessment.session
@Injectable({
  providedIn: 'root'
})
export class LoginauthService {
  session:string; //the encoded URL key to identify the session
  sessionID:number; //the unique auto-incremented key in the database
  teamSize:Number[];
  questionNumber:number = 0;


  constructor(private router: Router,
  private http: HttpClient) { 
    let s = this.getSession(); //When service is first activated, check if session data is stored in browser cookies, and pull it into client
    if(s){
      this.session = s.sessionID;
    }
    
  }

  storeSession(team:string, email:string, port:any){ //Sends request to the backend to create a unique session.

    //These 3 items are used for skipping the signup page when a team takes repeat assessments. 
    localStorage.setItem('team', team);
    localStorage.setItem('email', email);
    localStorage.setItem('portfolio', JSON.stringify(port));

    return this.http.put<Session>(environment.apiTarget+`/session/create/${port.id}`, {sessionID:team,email:email}) //API endpoint
  }

  updateSessionProgress(url){ //Stores URL of current page in browser cookie
    localStorage.setItem('lastPage', decodeURIComponent(url)); //url has to be decoded, as the string will have URI replacers (i.e. whitespace = '%20') otherwise
  }


  //Getters
  getCurrentSessionID(){ 
    if(!this.sessionID){
      return localStorage.getItem('authSessionID');
    }
    return this.sessionID;
  }
  getSession(){
    if(localStorage.getItem('authSession') && localStorage.getItem('lastPage')){
      let auth = new Session(localStorage.getItem('authSession'), localStorage.getItem('lastPage'), parseInt(localStorage.getItem('authSessionID')))
      
      return auth;
    }
    return null;
  }

  getRepeatSession(){
    let team = localStorage.getItem('team');
    let email = localStorage.getItem('email');
    let port = localStorage.getItem('portfolio')
    if(team && email && port){
      return {team:team, email:email, port:JSON.parse(port)}
    }
  }

  getSessionHistory(urlKey:string):any{
    if(urlKey == null){
      urlKey = localStorage.getItem('authSession')
    }
    return this.http.get<any>(environment.apiTarget+`/${urlKey}/history`)
  }


  //Stores demographic data from the team-members page
  storeTeamSize(size, loc){
    this.teamSize = size;
    let id = this.getCurrentSessionID();
    return this.http.put<any>(environment.apiTarget+`/session/demographic/${id}`,{location:loc, size:size[0]})
  }

  sessionComplete(urlNum){
    console.log(urlNum)
    if(this.session && urlNum == 2){
      return this.http.put<any>(environment.apiTarget+`/${this.session}/close`, null)
    }
    else{
      return from('1')
    }
  }
  setCategory(catID){
    let team = localStorage.getItem('team');
    let id = this.getCurrentSessionID();
    return this.http.put<string>(environment.apiTarget+`/${id}/cat/${catID}`, team, {responseType: 'text' as 'json'});
  }

  storeAssessmentAnswers(qid, choice){
    let answers = JSON.parse(localStorage.getItem('answers'));
    if(answers == null){
      answers = []
    }
    answers[qid] = choice;
    localStorage.setItem('answers', JSON.stringify(answers));
  }
  getAssessmentAnswer(qid){
    let answers = JSON.parse(localStorage.getItem('answers'));
    return (answers ? answers[qid] : null);
  }
  //Removes browser cookies. Called on outcome page
  clearSession(){
    this.logSession();
    localStorage.removeItem('authSession')
    localStorage.removeItem('lastPage')
    localStorage.removeItem('authSessionID')
  }

  logSession(){
    console.log(localStorage.getItem('authSession'),
    localStorage.getItem('lastPage'),
    localStorage.getItem('authSessionID'))
  }
}
