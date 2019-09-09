import { Injectable } from '@angular/core';
import { AnswerService } from './answer.service';
import { Outcome } from '../../outcome/outcome.component';
import { HttpClient } from '../../../../node_modules/@angular/common/http';
import { LoginauthService } from '../loginauth.service';
import { environment } from '../../../environments/environment';
import { stringify } from '@angular/core/src/render3/util';


export class IndivOutcome{ //Outcome class with expanded set of variables for the detail needed on the results page
  models: any;
  constructor(public catID:number,
    public compID:number,
    public catName: string,
    public compName: string,
    public outcomeText: string,
    public level: number,
    public outcomes:IndivOutcome[]
    ){}
}



//Hits endpoints in OutcomeResource under com.assessment.assessment.outcome and MailService in com.assessment.assessment.mail
@Injectable({
  providedIn: 'root'
})
export class OutcomesService {
  display:string[] =[]
  ciMap:Object = {}
  plans:Outcome[] = []
  constructor(private answerService:AnswerService,
    private loginauth: LoginauthService,
    private http:HttpClient) { 
    
  }

  deliverOutcomes(key){
    return this.http.get<any>(environment.apiTarget+`/${key}/outcome/history`)
  }

  deliverEmail(link, key){
    link = link.concat('/e_l');
    return this.http.put<string>(environment.apiTarget+`/${key}/outemail`, {recipient: null, url:link});
  }
}
