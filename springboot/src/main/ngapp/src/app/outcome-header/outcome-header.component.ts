import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { LoginauthService } from '../service/loginauth.service';

@Component({
  selector: 'app-outcome-header',
  templateUrl: './outcome-header.component.html',
  styleUrls: ['./outcome-header.component.css']
})
export class OutcomeHeaderComponent implements OnInit {

  constructor(private router:Router,
    private loginAuth:LoginauthService) { }
  @Output() viewEvent: EventEmitter<number> = new EventEmitter();

  viewMessage(viewNumber){
    this.viewEvent.emit(viewNumber);
  }
  ngOnInit() {
  }
  start(){
    this.loginAuth.clearSession();
    this.router.navigate([''])
  }

}
