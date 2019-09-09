import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OutcomeHeaderComponent } from './outcome-header.component';

describe('OutcomeHeaderComponent', () => {
  let component: OutcomeHeaderComponent;
  let fixture: ComponentFixture<OutcomeHeaderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OutcomeHeaderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OutcomeHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
