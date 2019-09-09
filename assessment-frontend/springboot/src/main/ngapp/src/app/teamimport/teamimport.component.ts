import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
var json = require('src/assets/teams.json');

export class Team{
  constructor(
    public id:number,
    public name:string
  ){}
}
@Component({
  selector: 'app-teamimport',
  templateUrl: './teamimport.component.html',
  styleUrls: ['./teamimport.component.css']
})
export class TeamimportComponent implements OnInit {
  idx:number = 0;
  constructor(private http:HttpClient) { }


  recurAddTeam(){
    if(json[this.idx] == null){return null}

    let team = new Team(json[this.idx]["ID"], json[this.idx]["PROJECT / TEAM TITLE"]);
    this.http.put<Team>(environment.apiTarget+'/team', new Team(team["ID"], team["PROJECT / TEAM TITLE"])).subscribe(
      res=>{this.idx++;
        //console.log(team)
        return this.recurAddTeam()}
    )
  }
  ngOnInit() {
    this.recurAddTeam();
  //   json.forEach((team)=>{
      
  //     this.http.put<Team>(environment.apiTarget+'/team', new Team(team["ID"], team["PROJECT / TEAM TITLE"])).subscribe(
  //       res=>{//console.log(team)}
  //     )
      
  // })
  }

}
