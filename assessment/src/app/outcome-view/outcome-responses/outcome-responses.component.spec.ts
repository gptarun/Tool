import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OutcomeResponsesComponent } from './outcome-responses.component';

describe('OutcomeResponsesComponent', () => {
  let component: OutcomeResponsesComponent;
  let fixture: ComponentFixture<OutcomeResponsesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OutcomeResponsesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OutcomeResponsesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
