import { Component, OnInit, Renderer, ViewChild, ViewEncapsulation, ViewChildren, QueryList } from '@angular/core';
import { ActivatedRoute, Router} from '../../../node_modules/@angular/router';
import { OutcomesService, IndivOutcome } from '../service/data/outcomes.service';
import { AnswerService } from '../service/data/answer.service';
import { LoginauthService } from '../service/loginauth.service';
import { Answer } from '../question/question.component';
import linkifyHtml from 'linkifyjs/html';
import { OutcomeViewComponent } from '../outcome-view/outcome-view.component';

//Component for results page of the assessment. Important!
//Contains class component for Angular Bootstrap Radar Charts. 
export class RadarChartComponent {
  public chartType: string = 'radar';

  public chartDatasets: Array<any> = [ //{data:[], label:}
    
  ];

  public chartLabels: Array<any> = [];

  public chartColors: Array<any> = [ //look at createChart function for example of values that go here
  ];

  public chartOptions: any = {
    responsive: true,
    scale: {
      ticks: {  //This option makes sure the charts span from 0 to 5
          beginAtZero:true,
          max:5
      }
    }
  };
  public chartClicked(e: any): void { }
  public chartHovered(e: any): void { }
}
export class Outcome{ //Object for putting outcomes in (used in outcome service probably)
  constructor(public area:number,
    public category:number,
    public competency:number,
  public outcomes:string[]){}
}

@Component({
  selector: 'app-outcome',
  templateUrl: './outcome.component.html',
  styleUrls: ['./outcome.component.css']
})
export class OutcomeComponent implements OnInit {

  //variables related to 'learning' options.
  outcomes:IndivOutcome[] = [];
  outcomesByCategory:IndivOutcome[][] = [];
  outcomeChecked:boolean[][] = [];

  outcomeDisabled:boolean[][] = [];

  //variable for the 'models' list. Models and Answers both use outcomeChecked to determine visibility
  // models: IndivOutcome[] = [];
  // modelsByCategory: IndivOutcome[][] = [];
  //variables for the 'responses' list
  answersByCategory:Answer[][] = [];

  radar:RadarChartComponent[] = [];
  default:boolean = true; //Determines if page is in the state that it is in on render, i.e. all of the radar charts are visible at once
  currentActive:any; //Which category has been focused by the user

  sessionHistory:any[] = [];

  //Variables used exclusively to prevent Internet Explorer from breaking everything's formatting
  ieBG:string;
  ieWidth:string;

  //Variables tied directly to HTML elements, used for modifying styles in the typescript
  @ViewChild('learninglist') list;
  @ViewChild('ieChartFlip') ieChartFlip;
  @ViewChild('ieChartFlipList') ieChartFlipList;
  
  outcomeViews: boolean[] = [true];
  compSets: any = [];
  
  
  constructor(private loginAuth: LoginauthService,
    private route: ActivatedRoute,
    private outcomeService: OutcomesService,
    private answerService: AnswerService,
    private renderer: Renderer) { }

  ngOnInit() {
    //category and competency sets for organizing charts and such
    let catSet = [];
    

    //unique key in the url, used to pull outcomes for that session
    let urlKey = this.route.snapshot.params['session']
    this.loginAuth.sessionComplete(this.route.snapshot.url.length).subscribe( //Set session to complete (sessions have to have a completedOn date to be shown on the outcome page)
      emptyResponse => { 
        // this.loginAuth.getSessionHistory(urlKey).subscribe( //Get sessions that have been completed by this team (max 2 per category)
        //   sessionResponse => {
        //     this.sessionHistory = sessionResponse
        //   }
        //   ) 

      
      this.outcomeService.deliverOutcomes(urlKey).subscribe(
        response=> {
          this.sessionHistory = response.sessionHistory;
          this.outcomes = response.outcomes;
          //this.models = response.models;
          this.categorize(); //Reorganizes the outcomes and models into 2D arrays correlated to the categories
          
          if(this.route.snapshot.url.length == 2){ //Really stupid way to tell whether or not the user revisiting the page with the emailed link (if they are revisiting the page, do not email them again)
            //url.length == 2 when it is their first time on the page after completing the assessment
            localStorage.removeItem("answers");
            this.outcomeService.deliverEmail(window.location.href, urlKey).subscribe(
              res=>{
              }
            )
          }
          this.outcomes.forEach((o)=>{ //Create category and competency breakdowns to create the charts with
            if(!this.catExists(catSet, o.catID)){
              catSet.push({name: o.catName, id: o.catID});
              this.compSets[o.catID] = [];
            }
            this.compSets[o.catID].push({name:o.compName, level:o.level})
          })
          catSet.forEach((cat)=>{this.createChart(cat);})
          this.answerService.getAnswersHistory(urlKey).subscribe( //Somewhat misleading, because it actually returns a list of objects that contain both the question and the answer given
            response=>{
              
              this.categorizeAnswer(response) //Same thing as categorize but for questions/answers
              if(this.route.snapshot.params['element']){
                this.receiveMessage(parseInt(this.route.snapshot.params['element']))
              }
              else{
                this.receiveMessage(2); //Immediately set view to the Learning Catalog
              }
              if(this.route.snapshot.url.length == 2){
                this.loginAuth.clearSession();
              }
            }
          )
          //this.setActiveCategory(this.outcomesByCategory[this.answersByCategory.length-1])
          

        }
      )
    
  })
      
    
    
    
  
  }


  createChart(cat){
    let dataset = {data:[], label: cat.name}
    dataset.data = this.compSets[cat.id].map((comp)=>{if(comp.level != 0)return comp.level}) //The chart dataset only takes numbers, so the competencies are filtered down to their maturity level
    let labels = this.compSets[cat.id].map((comp)=>{if(comp.level !=0)return comp.name}) //Same deal with the chart labels
    let newChart = new RadarChartComponent;
    newChart.chartDatasets = [dataset];
    newChart.chartLabels = labels;
    newChart.chartColors = [{ //To make the charts distinct from each other, color each one with a random set of colors
      backgroundColor: `rgba(${this.randomColor()},${this.randomColor()},${this.randomColor()}, .2)`,
      borderColor: 'rgba(200, 99, 132, .7)',
      borderWidth: 2,
    }]
    this.radar.push(newChart);
    
  }
  catExists(catSet, id){ //Used to determine whether or not to push a new element into the catset array
    for(let i = 0; i < catSet.length; i++){
      if(catSet[i].id == id){
        return true;
      }
    }
    return false;
  }

  randomColor(){
    return Math.floor(Math.random() * 255)
  }

  flip(event){ //Handles the flip effect that the charts/learning section does by applying a class with a rotation on it
    let isIEOrEdge = /msie\s|trident\/|edge\//i.test(window.navigator.userAgent) //Have to do extra for IE
    let oldClasses = this.list.nativeElement.getAttribute('class');
    let oldClassesIE = this.ieChartFlip.nativeElement.getAttribute('class');
    let oldClassesIEList = this.ieChartFlipList.nativeElement.getAttribute('class');
    if(isIEOrEdge){
      this.ieWidth = '100%';
      this.ieBG = 'transparent';
    }
    if(oldClasses.includes('is-flipped')){
      this.renderer.setElementAttribute(this.list.nativeElement, "class", 'charts');
      this.renderer.setElementAttribute(this.ieChartFlip.nativeElement, "class", 'chart-side')
      this.renderer.setElementAttribute(this.ieChartFlipList.nativeElement, "class", 'list-group')
    } else{
      this.renderer.setElementAttribute(this.list.nativeElement, "class", oldClasses + ' is-flipped');
      this.renderer.setElementAttribute(this.ieChartFlip.nativeElement, "class", oldClassesIE + ' is-flipped');
      this.renderer.setElementAttribute(this.ieChartFlipList.nativeElement, "class", oldClassesIEList + ' is-flipped')
    }

  }

  categorize(){ //filters outcomes and models into their respective categories in a 2d array
    let categories = this.outcomes.map(item => item.catName).filter((val, idx, self) => self.indexOf(val) === idx)
    console.log(this.outcomes)
    categories.forEach((cat, idx)=>{
      this.outcomeChecked.push([]);
      this.outcomesByCategory.push(this.outcomes.filter((val)=>val.catName == cat).map((filteredVal)=>this.formatLinks(filteredVal)))
      //this.modelsByCategory.push(this.models.filter((val)=>val.catName == cat))
      

      this.findRecommended(idx, 2);
    })
    console.log(this.outcomesByCategory)
   
  }

  categorizeAnswer(answers){ //same thing as categorize; had to do it a bit differently because those objects are different on the Java side
    let categories = answers.map(item => item.answer.category).filter((val, idx, self)=>self.indexOf(val) === idx)
    categories.forEach((cat)=>{
      this.answersByCategory[cat] = (answers.filter((val)=>val.answer.category == cat))
    })
    
  }

  findRecommended(index, cutoff){ //Finds the n competencies with the lowest levels in their respective categories, where n = cutoff
    //Competencies with a level of 0 are not factored into the calculation
    let lowest = [...this.outcomesByCategory[index]]; //Makes a copy of the array (without doing [... ] it copies it by reference and the sorting messes things up elsewhere)
    lowest = lowest.filter((comp)=>{return comp.level != 0}).sort(function(a, b){return a.level - b.level})
    
    for(let i = 0; i < lowest.length; i++){
      

      if(i < cutoff){
        this.outcomeChecked[index][lowest[i].compID] = true;
      }
      else{
        this.outcomeChecked[index][lowest[i].compID] = false;
      }
    }
  }

  formatLinks(learningItem){
    let regex = new RegExp("<li></li>", 'g')
    learningItem.outcomeText = learningItem.outcomeText.replace(regex, "")
    learningItem.outcomeText = linkifyHtml(learningItem.outcomeText,{
      defaultProtocol: 'https',
      format: (value, type)=>{
        value = 'Link'
        return value;
      }
    })
    return learningItem;
  }

  resetToRecommended(){ //Returns the checkboxes and radar charts to their default state. Active category stays active.
    this.default = true;
    for(let i = 0; i < this.outcomesByCategory.length;i++){
      this.findRecommended(i, 2);
    }
  }
  setActiveCategory(cat){ //Focuses a category. Reveals Actions, Models, and Responses for selected competencies in that category
    this.default = false;
    cat.active = true;
    if(this.currentActive && this.currentActive != cat){
      this.currentActive.active = false
    }
    this.currentActive = cat
  }

  receiveMessage($event){

   
   
    this.outcomeViews = this.outcomeViews.map((view, idx)=>{
      if(idx == $event){ return true }
      else{ return false }
    })
    if($event >= this.outcomeViews.length || !this.outcomeViews[$event]){
      this.outcomeViews[$event] = true;
      this.outcomeViews
    }
    
    
  }
}
