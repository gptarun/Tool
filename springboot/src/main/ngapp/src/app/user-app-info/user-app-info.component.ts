import { Component, OnInit } from '@angular/core';
import { LoginauthService } from '../service/loginauth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-info',
  templateUrl: './user-app-info.component.html',
  styleUrls: ['./user-app-info.component.css']
})
export class UserAppInfoComponent implements OnInit {
  team: any = "";
  email: any = "";
  portfolio: any = ""

  constructor(private router: Router,
    private loginauth: LoginauthService) {
  }
  ngOnInit() {
    let credentials = this.loginauth.getRepeatSession();
    this.team = credentials.team
    this.email = credentials.email
    this.portfolio = credentials.port
  }
  logout() {
    localStorage.removeItem('team');
    localStorage.removeItem('email');
    localStorage.removeItem('porfolio');
    this.loginauth.clearSession();
    this.router.navigate(['/start'])
  }

}