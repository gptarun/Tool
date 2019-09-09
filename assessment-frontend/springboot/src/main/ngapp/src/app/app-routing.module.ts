import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SignupComponent } from './signup/signup.component';
import { QuestionComponent } from './question/question.component';
import { OutcomeComponent } from './outcome/outcome.component';
import { ErrorComponent } from './error/error.component';
import { RouteGuardService } from './service/route-guard.service';
import { TeamMembersComponent } from './team-members/team-members.component';
import { ApplicationsComponent } from './applications/applications.component';
import { InstructionComponent } from './instruction/instruction.component';
//import { EditComponent } from './edit/edit.component';
import { BackgroundComponent } from './background/background.component';
import { AssessStartComponent } from './assess-start/assess-start.component';
const routes: Routes = [
  {path:'', component: SignupComponent, canActivate: [RouteGuardService]},
  {path:'bg', component: SignupComponent, canActivate: [RouteGuardService]},
  {path:'start', component: SignupComponent, canActivate: [RouteGuardService]},
  {path:'start/:team', component: TeamMembersComponent, canActivate: [RouteGuardService]},
  {path: 'app', component: ApplicationsComponent, canActivate: [RouteGuardService]},
  {path:'ins', component: InstructionComponent, canActivate: [RouteGuardService]},
  {path:'question/:id', component: QuestionComponent, runGuardsAndResolvers: 'always', canActivate: [RouteGuardService]},
  {path: 'outcome/:session', component: OutcomeComponent, canActivate: [RouteGuardService]},
  {path: 'outcome/:session/e_l', component:OutcomeComponent},
  {path: 'outcome/:session/e_l/:element', component:OutcomeComponent},
  //{path: 'secret/test', component: EditComponent},
  {path: 'assess', component: AssessStartComponent, canActivate: [RouteGuardService]},
  //{path: 'secret/team', component: TeamimportComponent},
  //{path: 'secret/outcome', component: OutcomeEditComponent},
  {path: '**', component: ErrorComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
