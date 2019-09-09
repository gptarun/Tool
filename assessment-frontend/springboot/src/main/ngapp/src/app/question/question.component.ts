import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, NavigationEnd } from '../../../node_modules/@angular/router';
import { QuestionsService } from '../service/data/questions.service';
import { AnswerService } from '../service/data/answer.service';
import { ProgressService } from '../visual/progress.service';
import { LoginauthService } from '../service/loginauth.service';

export class Question { //Class for Questions, kept mostly equivalent to the Question class in backend
  constructor(
    public id: number,
    public description: string,
    public choices: string[],
    public levels: number[],
    public weight: number,
    public area: number,
    public category: number,
    public competency: number,
    public catString: string,
    public compString: string
  ){

  }
}

export class Answer{ //Not equivalent to the backend Answer class. Mostly exists for easy Answer submissions
  constructor(
    public id: number,
    public choice: number,
    // public area:number,
     public category: number,
     public competency: number,
    // public weight: number
  ){

  }
}


@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.css']
})

//Component for the questions asked in the assessment. Unique in that it navigates to itself when moving to a new question, which requires a special property to be set
export class QuestionComponent implements OnInit {

  currentQuestion:Question; //Question object pulled from backend, used to populate components on the page
  id:number; //Question id, part of the page URL
  answer:number;
  prevQuestionRoute;

  constructor(private route: ActivatedRoute,
  public router: Router,
  private loginAuth: LoginauthService,
  private questionsService: QuestionsService,
  private answerService: AnswerService,
  private progress: ProgressService) {
    this.router.routeReuseStrategy.shouldReuseRoute = function(){ //Forces page to render when going from one question page to another.
      return false;
    }
   }

  ngOnInit() {
    this.getCurrentQuestion();

  }

  getCurrentQuestion(){ //Retrieve question from database; if there is no question matching the ID, go back to the first question
    this.id = parseInt(this.route.snapshot.params['id']);
    let storedAnswer = this.loginAuth.getAssessmentAnswer(this.id);
    
    this.questionsService.getQuestion(this.id).subscribe(
      response=>{
        if(response){
          this.currentQuestion = response
          console.log(storedAnswer)
          if(storedAnswer){
            this.answer = storedAnswer
          }
          this.questionsService.getPrevQuestion(this.currentQuestion.id, this.currentQuestion.category).subscribe(
            prev=>{
              if(prev){
                this.prevQuestionRoute = ['/question', prev.id] //Determines route to the previous question on pageload. Doubles as a check to see if the back button should be displayed
              }
            })
        }
        else{
          this.loginAuth.updateSessionProgress('/question/'+Number(1))
          this.router.navigate(['question', 1])
        }
      }
    )
  }
  submitAnswer(){ //Puts answers in the database, then checks for next question
    //let key = this.currentQuestion.area.toString()+this.currentQuestion.category.toString()+this.currentQuestion.competency.toString()
    this.answerService.storeAnswer(this.id, (this.answer != -1 ?this.currentQuestion.levels[this.answer]:-1),  this.loginAuth.getCurrentSessionID()).subscribe(
      response=>{
        this.loginAuth.storeAssessmentAnswers(this.id, this.answer);
        this.questionsService.checkIfMoreQuestions(this.id, this.currentQuestion.category).subscribe( //Checks to see if there is a question matched to id+1. If not, user is at end of assessment
          response=>{
            this.progress.incProgress(); //Updates progress bar
            if(response){
              let next = response.id;
              this.loginAuth.updateSessionProgress(`/question/${next}`)
              this.router.navigate(['question', next])
            }
            else{
              this.loginAuth.updateSessionProgress('/outcome/'+this.loginAuth.session)
              this.router.navigate(['outcome', this.loginAuth.session])
            }
          }
        )
      }
    );
  }

  goBack(){ //Navigates back to the previous question
      // this.questionsService.getPrevQuestion(this.currentQuestion.id, this.currentQuestion.category).subscribe(
      //   response=>{
      //     if(response){
      //       let prev = response.id;
      //       this.progress.decProgress();// Updates progress bar
      //       this.loginAuth.updateSessionProgress(`/question/${prev}`)
      //       this.router.navigate(['question', prev])
      //     }
      //   }
      // ) 
      this.progress.decProgress();// Updates progress bar
      this.loginAuth.updateSessionProgress(`/question/${this.prevQuestionRoute[1]}`)
      this.router.navigate(this.prevQuestionRoute)
  }

  setAnswer(answer){
    this.answer = answer;
    console.log(this.answer)
  }
}
