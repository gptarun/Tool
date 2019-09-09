import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '../../node_modules/@angular/common/http';
import {FormsModule} from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { SignupComponent } from './signup/signup.component';
import { QuestionComponent } from './question/question.component';
import { OutcomeComponent } from './outcome/outcome.component';
import { FooterComponent } from './footer/footer.component';
import { ErrorComponent } from './error/error.component';
import { TeamMembersComponent } from './team-members/team-members.component';
import { ApplicationsComponent } from './applications/applications.component';
import { InstructionComponent } from './instruction/instruction.component';
//import { EditComponent } from './edit/edit.component';
import { TeamimportComponent } from './teamimport/teamimport.component';
import {ChartsModule, WavesModule, MDBBootstrapModule, DropdownModule} from 'angular-bootstrap-md';
import {MatSelectModule} from '@angular/material/select';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatDialogModule} from '@angular/material/dialog'
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner'; 
import { BackgroundComponent } from './background/background.component';
import { AssessStartComponent } from './assess-start/assess-start.component';
import { OutcomeHeaderComponent } from './outcome-header/outcome-header.component';
import { OutcomeResponsesComponent } from './outcome-view/outcome-responses/outcome-responses.component';
import { OutcomeViewComponent } from './outcome-view/outcome-view.component';
import { OutcomeLearningCatalogComponent } from './outcome-view/outcome-learning-catalog/outcome-learning-catalog.component';
import { OutcomeSummaryComponent } from './outcome-view/outcome-summary/outcome-summary.component';
import { IntroWindowComponent } from './intro-window/intro-window.component';
import { AssessmentOutcomeHeaderComponent } from './assessment-outcome-header/assessment-outcome-header.component';
import { UserAppInfoComponent } from './user-app-info/user-app-info.component';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { ModalModule } from 'ngx-bootstrap/modal';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SignupComponent,
    QuestionComponent,
    OutcomeComponent,
    FooterComponent,
    ErrorComponent,
    TeamMembersComponent,
    ApplicationsComponent,
    InstructionComponent,
    //EditComponent,
    TeamimportComponent,
    BackgroundComponent,
    AssessStartComponent,
    IntroWindowComponent,
    OutcomeHeaderComponent,
    OutcomeResponsesComponent,
    OutcomeViewComponent,
    OutcomeLearningCatalogComponent,
    OutcomeSummaryComponent,
    AssessmentOutcomeHeaderComponent,
    UserAppInfoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ChartsModule,
    WavesModule,
    MDBBootstrapModule.forRoot(),
    BrowserAnimationsModule,
    MatSelectModule,
    MatCheckboxModule,
    MatDialogModule,
    MatExpansionModule,
    MatProgressSpinnerModule,
    DropdownModule.forRoot(),
    BsDropdownModule.forRoot(),
    TooltipModule.forRoot(),
    ModalModule.forRoot()
    
  ],
  entryComponents: [IntroWindowComponent],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
