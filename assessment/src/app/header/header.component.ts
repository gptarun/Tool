import { Component, OnInit } from '@angular/core';
import { ProgressService } from '../visual/progress.service';
import { Router, NavigationEnd } from '../../../node_modules/@angular/router';
import { Subscription } from '../../../node_modules/rxjs';
import { IntroWindowComponent } from '../intro-window/intro-window.component';
import { MatDialog } from '@angular/material/dialog';

//Progress bar that increases in length as user progresses through assessment. Does not work properly if user resumes assessment in the middle.
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  width:string = "0%";  //bound to the html element; when it changes, the width of the bar changes
  subscription:Subscription
  hide: boolean;
  constructor(public progress: ProgressService,
  public router: Router,
  public dialog: MatDialog) { 
    router.events.subscribe((val)=>{
      
      if(val instanceof NavigationEnd){
        if (val.url == '/start'){
          this.width = "0%"
        }
        else{
          this.hide = false;
        }
      }
    })
    this.subscription = this.progress.progItem.subscribe(//Sort of an event listener that changes width after the service activates
      item => {this.width = item.toString()+"%"
      }
    )
  }

  ngOnInit() {
  }

  openIntroDialog(){
    const dialogRef = this.dialog.open(IntroWindowComponent,
      {width: '900px', panelClass:'intro-dialog'})
  }
  
}
