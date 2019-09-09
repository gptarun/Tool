import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OutcomeViewComponent } from './outcome-view.component';

describe('OutcomeViewComponent', () => {
  let component: OutcomeViewComponent;
  let fixture: ComponentFixture<OutcomeViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OutcomeViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OutcomeViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
