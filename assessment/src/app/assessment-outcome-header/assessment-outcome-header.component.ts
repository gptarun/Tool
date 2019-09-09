import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginauthService } from '../service/loginauth.service';

@Component({
  selector: 'app-assessment-outcome-header',
  templateUrl: './assessment-outcome-header.component.html',
  styleUrls: ['./assessment-outcome-header.component.css']
})
export class AssessmentOutcomeHeaderComponent implements OnInit {

  constructor(private router:Router,
    private loginAuth:LoginauthService) { }

  hasCompletedSessions:boolean;
  ngOnInit() {
    this.loginAuth.getSessionHistory(null).subscribe(
      result =>{ this.hasCompletedSessions = (result.length != 0)}
    )
  }

  start(){
    this.loginAuth.clearSession();
    this.router.navigate([''])
  }

  toOutcomes(section){
    let urlKey = this.loginAuth.getSession();
    console.log(urlKey)
    this.router.navigate(['/outcome',urlKey.sessionID,'e_l',section])
  }


}
