import { Component, OnInit } from '@angular/core';
import { Router } from '../../../node_modules/@angular/router';
import { LoginauthService } from '../service/loginauth.service';

//This functionality could probably be brought into the background component but it has not been done yet
@Component({
  selector: 'app-instruction',
  templateUrl: './instruction.component.html',
  styleUrls: ['./instruction.component.css']
})
export class InstructionComponent implements OnInit { //Nothing special going on here

  constructor(private router: Router,
  private loginAuth: LoginauthService) { }

  ngOnInit() {
  }


  next(){
    this.loginAuth.updateSessionProgress('/start')
    this.router.navigate(['start'])
  }
}
