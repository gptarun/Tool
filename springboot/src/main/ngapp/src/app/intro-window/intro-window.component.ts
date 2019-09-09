import { Component, OnInit, ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'app-intro-window',
  templateUrl: './intro-window.component.html',
  styleUrls: ['./intro-window.component.css']
})
export class IntroWindowComponent implements OnInit {

  textComponents:string[] = [
    
    `
    <img src="assets/assessment-chart-2.png"></img>`,

    `
    <h3>What to expect</h3>
    
    <p>The TOOL is a persistent workspace unique to your team.  It provides the opportunity to have powerful conversations with your entire delivery team (including contractors and product owners if you have them).  Unless your resource manager is part of the delivery team, we recommend the “Evaluate” functionality be completed without the presence of a resource manager.  The “Evaluate” functionality provides a holistic view across DevOps, Agile and QA.  The TOOL streamlines your experience generating outcome-based results immediately.  Results include recommended learning tailored to your team.  When you’ve reviewed the results and determined next steps, share your action plan with your resource manager(s) to gain buy-in and support.  Only together can you work towards delivering value faster, improving quality and reliability, plus have a happier team!</p>

    <h3>Why to use the TOOL</h3>
    
    <p>The TOOL is intended to help teams meet their goals.  It is a tool – not a test.  By achieving our Tech 2.0 goals, we can deliver more solutions of higher quality in less time and with less stress.  We can’t just ‘work faster’ – we need to evolve our culture and adopt Agile, DevOps and product-centric ways of working.  Each team is responsible for their own unique journey.  If you aren’t sure where to start, the TOOL will help you identify what initial actions will have the highest impact outcomes.</p>

    <h3>The right time</h3>
    
    <p>It’s up to the team to decide when and how often to use the TOOL.  If your team is already working on an action plan based on recent results from a previous assessment like the DevOps assessment, it may not be the right time.  When you’re ready for more direction, use the TOOL to uncover additional areas to focus on.  As a general rule of thumb, “Evaluate” one area, form and execute an action plan for that area, then come back to the TOOL for more.    </p>
    `

  ]
  page:number = 0;
  constructor() { }

  ngOnInit() {
    this.page = 0;
  }

}
