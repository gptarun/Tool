import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssessStartComponent } from './assess-start.component';

describe('AssessStartComponent', () => {
  let component: AssessStartComponent;
  let fixture: ComponentFixture<AssessStartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssessStartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssessStartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
