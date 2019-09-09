import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginauthService } from '../service/loginauth.service';
import { QuestionsService } from '../service/data/questions.service';
import { AnswerService } from '../service/data/answer.service';
import { ProgressService } from '../visual/progress.service';


//The initial screens that explain what the assessment is for, etc.
//Cycles through multiple views, but its just one component being loaded. It changes its contents on button click
@Component({
  selector: 'app-background',
  templateUrl: './background.component.html',
  styleUrls: ['./background.component.css']
})
export class BackgroundComponent implements OnInit {
  //The text being seen by the user. Swaps out when user hits 'next'.
  //You can put in as many elements as you want; the logic for moving to the next section is based on the length of the array
  pageValue = [  
    {
      header: "Why take this team assessment",
      body:"This assessment is intended to help teams meet their goals. It is a tool—not a test. By achieving our Tech 2.0 goals, we can deliver more solutions of higher quality in less time and with less stress. We can’t just ‘work faster’- we need to evolve our culture and adopt Agile, DevOps and product-centric ways of working. Each team is responsible for their own unique journey. If you aren't sure where to start, this team assessment will help you identify what initial actions will have the highest impact outcomes."},
    {
      header:'What to expect',
      body:`Consider this as an opportunity to have powerful conversations with your entire delivery team (including contractors and product owners if you have them). Unless your resource manager is part of the delivery team, we recommend this team assessment to be completed without the presence of a resource manager. 
 
      This assessment provides a more holistic view across DevOps, Agile and QA. Rather than having three separate assessments, this new assessment streamlines your experience while also generating outcome-based results immediately after completion. Results will include learning journeys and training recommendations tailored to your team. 
       
      When you've reviewed the results and determined next steps, share the action plan with you resource manager to gain buy-in and support. Only together can you work towards delivering value faster, improving quality and reliability, plus have a happier team!
      `
    },
    {
      header:'The right time',
      body:`It's up to the team to decide when and how often to take this assessment. If your team is already working on an action plan based on recent results from a previous assessment like the DevOps assessment, it may not be the right time. When you're ready for more direction, take this assessment to uncover additional areas to focus on.
 
      As a general rule of thumb, we recommend taking this assessment no more than once a quarter to allow time to thoughtfully respond to results.
      `
    }
  ]
  id:number; //values corresponding to which element in the array to use for text
  
  //Holds the values to be seen at the current id
  currentHeader:string; 
  currentBody:string;

  constructor(private route: ActivatedRoute,
    private router: Router,
    private loginAuth: LoginauthService) {
     }

  ngOnInit() {
    this.id = 0;
    this.setHeaderAndBody();
  }

  setHeaderAndBody(){
    
    this.currentHeader = this.pageValue[this.id].header;
    this.currentBody = this.pageValue[this.id].body;
  }

  next(){ //button logic
    if(this.id == this.pageValue.length - 1){
      console.log(this.loginAuth.getSession()) 
      if(this.loginAuth.getSession()){
        this.router.navigate([this.loginAuth.getSession().url])
      }
      else{
        this.loginAuth.updateSessionProgress('/ins');
        this.router.navigate(['ins']);
      }
    }
    else{
      this.id++;
      this.setHeaderAndBody();
    }
  }

}
