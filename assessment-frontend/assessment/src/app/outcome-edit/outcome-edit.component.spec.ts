import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OutcomeEditComponent } from './outcome-edit.component';

describe('OutcomeEditComponent', () => {
  let component: OutcomeEditComponent;
  let fixture: ComponentFixture<OutcomeEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OutcomeEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OutcomeEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
