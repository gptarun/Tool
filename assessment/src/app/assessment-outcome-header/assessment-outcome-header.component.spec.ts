import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssessmentOutcomeHeaderComponent } from './assessment-outcome-header.component';

describe('AssessmentOutcomeHeaderComponent', () => {
  let component: AssessmentOutcomeHeaderComponent;
  let fixture: ComponentFixture<AssessmentOutcomeHeaderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssessmentOutcomeHeaderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssessmentOutcomeHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
