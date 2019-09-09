import { Component, OnInit } from '@angular/core';
import { LoginauthService } from '../service/loginauth.service';
import { Router, NavigationEnd } from '../../../node_modules/@angular/router';
import { DemographicsService } from '../service/demographics.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  email: string; //2-way bound variable for email field
  prod_group: string; 
  team: string; //2-way bound variable for team name field
  itPortfolio: any; //2-way bound variable for underlying value of IT Portfolio chosen
  portGroups: any[]; //Array of portfolios
  renderPage:boolean;

  constructor(private loginAuth:LoginauthService,
    private router:Router,
    private demoService: DemographicsService) {
      
     }

  ngOnInit() {
      //let session = this.loginAuth.getSession(); 
      let storedCredentials = this.loginAuth.getRepeatSession();
      if(storedCredentials){
        this.team = storedCredentials.team;
        this.email = storedCredentials.email;
        this.itPortfolio = storedCredentials.port;
        this.beginAssessment();
      }
      else{
        this.renderPage = true;
        this.demoService.getPortfolios().subscribe( //Retrieve portfolio/vertical structure to put into dropdown field
          response=>{
            console.log(response)
            this.portGroups = response;
          }
        )
      }  
  }

  beginAssessment(){
    if(!this.itPortfolio){
      return false
    }
    this.loginAuth.storeSession(this.team, this.email, this.itPortfolio).subscribe( //Creates a session in the database, returns the session object
      res=> {
        this.loginAuth.sessionID = res.dbId; //session objects contains auto-incremented id and encoded URL for the app to distinguish sessions with
        this.loginAuth.session = res.url;
        localStorage.setItem('authSessionID', res.dbId.toString()) //Store those in browser cookies, so user can go back to this session as long as they don't clear cookies
        localStorage.setItem('authSession', res.url)
        this.loginAuth.updateSessionProgress('/assess') //This function stores the url of the next page, for returning after closing the page
        this.router.navigate(['/assess']) //navigate to next page
      }
    )
  }
}
