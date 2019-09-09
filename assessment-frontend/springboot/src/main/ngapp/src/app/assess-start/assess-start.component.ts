import { Component, OnInit } from '@angular/core';
import { QuestionsService } from '../service/data/questions.service';
import { Router } from '@angular/router';
import { LoginauthService } from '../service/loginauth.service';

@Component({
  selector: 'app-assess-start',
  templateUrl: './assess-start.component.html',
  styleUrls: ['./assess-start.component.css', '../outcome-view/outcome-view.component.css']
})
export class AssessStartComponent implements OnInit {

  constructor(private questionService: QuestionsService,
    private router: Router,
    private loginAuth: LoginauthService) { }

    categories;
    competencies = [];
    ids;
  ngOnInit() {
    //Gets full set of questions. This allows empty categories to exist in the DB without erroneously providing links to take assessments on that category
    //by filtering category and competency names from existing questions.
    this.questionService.getAllQuestions().subscribe( 
      response => {
        this.categories = response.map(cat=>cat.catString).filter((val, idx, self)=>self.indexOf(val) === idx)
        this.ids = response.map(cat=>cat.category).filter((val, idx, self)=>self.indexOf(val)===idx)
        this.ids.forEach((id)=>{
          this.competencies[id] = response.filter((val)=>( val.category == id)).map(val=>val.compString).filter((val, idx, self)=>self.indexOf(val) === idx)
        })
        this.formatCompetencies();
      }
    )
  }

  formatCompetencies(){ //Converts the arrays of competencies per category into single strings in which competencies are delimited by ', '
    this.competencies.forEach((compSet, idx)=>{
      let compString = ""
      compSet.forEach((comp, idx2)=>{
        if(idx2 == compSet.length-1 && idx2 > 0){
          compString = compString.concat("and ") //For the last one, include that proper grammar
        }
        compString = compString.concat("<b>",comp,"</b>",", ")
      })
      compString = compString.slice(0, -2);
      this.competencies[idx] = compString;
    })
  }

  startAssessment(i){
    let category = this.ids[i];
    console.log(category);
    this.questionService.checkIfMoreQuestions(0, category).subscribe( //passing an argument of 0 will get the first question in any given category, since the lowest question id value is 1
      response=> {
        this.loginAuth.setCategory(category).subscribe(
          catResponse =>{
            let next = response.id;
            this.loginAuth.updateSessionProgress(`/question/${next}`)
            this.router.navigate(['question', next])
          }
        )
       
      }
    )
  }

}
