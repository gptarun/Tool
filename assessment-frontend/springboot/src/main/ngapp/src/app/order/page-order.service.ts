import { Injectable } from '@angular/core';
import { Router, Route } from '../../../node_modules/@angular/router';
import { QuestionsService } from '../service/data/questions.service';

@Injectable({
  providedIn: 'root'
})
export class PageOrderService {
  routes:any
  bookmark:number = 0;

  constructor(private router:Router,
  private qService: QuestionsService) {
    
    this.routes = this.router.config.map((route)=>{if(route.path != "" && route.path != "**")return (route.path)}).filter((path)=>{if(path) return path})
    ////console.log(routes)

  }

  
  test(){
    //console.log(this.routes)
  }
}
