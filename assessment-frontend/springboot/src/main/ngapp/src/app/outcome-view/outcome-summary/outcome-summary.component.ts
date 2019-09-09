import { Component, OnInit, Input } from '@angular/core';
import { OutcomeViewComponent } from '../outcome-view.component';
import { RadarChartComponent } from 'src/app/outcome/outcome.component';
import { IndivOutcome } from 'src/app/service/data/outcomes.service';

@Component({
  selector: 'app-outcome-summary',
  templateUrl: './outcome-summary.component.html',
  styleUrls: ['./outcome-summary.component.css', '../outcome-view.component.css']
})
export class OutcomeSummaryComponent extends OutcomeViewComponent implements OnInit {

  radarChart:RadarChartComponent;
  // catColors:any = 
  // {
  //   'Talent':'rgba(0, 70, 127, .3)',
  //   'Organization':'rgba(195, 0, 25, .3)',
  //   'Flow':'rgba(0, 185, 137, .3)',
  //   'Technical':'rgba(77, 180, 250, .3)',
  //   'Governance':'rgba(157, 166, 171, .3)'
  // }
  @Input() outcomes:IndivOutcome[];
  @Input() compSets:any;
  constructor() {
    super();
   }

  ngOnInit() {
    this.createChart();
  }

  createChart(){
    let competencies = this.outcomes.map(outcome=>outcome.compName).filter((val, idx, self) =>self.indexOf(val) === idx); //distinct set of competency names
    let categories = this.outcomes.map(item => item.catName).filter((val, idx, self)=>self.indexOf(val) === idx)
    let datasets = []
    let labels = []
    let colors = []
    let newChart = new RadarChartComponent;
    categories.forEach((cat, idx)=>{
      let dataSet = {data:[], label: cat}
      //dataSet.data = this.compSets[idx].map((comp)=>{if(comp.level != 0)return comp.level}) //The chart dataset only takes numbers, so the competencies are filtered down to their maturity level
      let labels = this.compSets[idx].map((comp)=>{if(comp.level != 0)return comp.name}) //Same deal with the chart labels
      labels = labels.filter((label) => {return label})
     
      newChart.chartLabels = newChart.chartLabels.concat(labels);
      this.compSets[idx].forEach((comp, idx2)=>{
        if(comp.level != 0){
          dataSet.data[newChart.chartLabels.indexOf(comp.name)] = comp.level
        }
      })
      dataSet.data.push(null);
      newChart.chartDatasets.push(dataSet);
      newChart.chartColors.push({ //To make the charts distinct from each other, color each one with a random set of colors
        backgroundColor: this.catColors[cat],
        borderColor: 'rgba(0, 120, 210, .5)',
        borderWidth: 1,
      })
    })
    
    // newChart.chartColors = [{ //To make the charts distinct from each other, color each one with a random set of colors
    //   backgroundColor: `rgba(${this.randomColor()},${this.randomColor()},${this.randomColor()}, .2)`,
    //   borderColor: 'rgba(200, 99, 132, .7)',
    //   borderWidth: 2,
    // }]
    //this.radar.push(newChart);
    this.radarChart = newChart;
  }

  randomColor(){
    return Math.floor(Math.random() * 255)
  }
}
