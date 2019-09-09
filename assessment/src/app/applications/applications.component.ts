import { Component, OnInit } from '@angular/core';
import { ApplicationsService } from '../applications.service';
import { Router } from '../../../node_modules/@angular/router';
import { LoginauthService } from '../service/loginauth.service';

@Component({
  selector: 'app-applications',
  templateUrl: './applications.component.html',
  styleUrls: ['./applications.component.css']
})
export class ApplicationsComponent implements OnInit {

  apps:string = ""//dynamically sized list of work items team performs
  errorMessage:string;

  constructor(private router: Router,
    private loginAuth: LoginauthService,
  private appService:ApplicationsService) {
  }

  appSubmit(){ //Validates that an option was selected before submitting values to database
 
    if(this.apps == ""){
        
      this.errorMessage = "Please select a value"
      return false;
    }
    this.appService.storeApps(this.apps).subscribe(
      response=>{
        this.loginAuth.updateSessionProgress('/assess')
        this.router.navigate(['assess'])
    
      }
    )
    
  }
  ngOnInit() {
   
  }

}
