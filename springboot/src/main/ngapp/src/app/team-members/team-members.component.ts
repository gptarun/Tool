import { Component, OnInit } from '@angular/core';
import { Router } from '../../../node_modules/@angular/router';
import { LoginauthService } from '../service/loginauth.service';

@Component({
  selector: 'app-team-members',
  templateUrl: './team-members.component.html',
  styleUrls: ['./team-members.component.css']
})
export class TeamMembersComponent implements OnInit {
  jobs:string[]
  jobCount:Number[]
  fillMessage:string
  radioMessage:string
  team_loc:string

  constructor(private router: Router, 
  private loginAuth: LoginauthService) { //Note: the structures used are designed for there to be multiple job fields, but current requirements do not need that
    this.jobs = [
      'Enter Number'
    ]
    this.jobCount=[];
    
   }

   teamSize(){ //Called on submit. Validates that user input appropriate values, and then stores values in the database
     //console.log(this.jobCount)
     let pass = true;
     if(this.jobCount.length != this.jobs.length){
       this.fillMessage = "Please enter a number."
       pass = false;
     }
     else{
       this.fillMessage = null;
     }
     if(this.team_loc == null){
       this.radioMessage = "Please select one of the radio button options."
      pass = false
     }
     else{
       this.radioMessage = null;
     }
     for(var i = 0; i < this.jobCount.length;i++){
       if(this.jobCount[i] < 1 || this.jobCount[i] > 50){
        this.fillMessage = "Please enter a team size between 1 and 50."
        pass =  false;
      }
     }
     if(pass == false){
       return false;
     }
     this.loginAuth.storeTeamSize(this.jobCount, this.team_loc).subscribe(
       response =>{
        this.loginAuth.updateSessionProgress('/app')
        this.router.navigate(['app'])
       }
     )
     

   }
   
  ngOnInit() {
    
  }

}
