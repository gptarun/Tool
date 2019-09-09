import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IntroWindowComponent } from './intro-window.component';

describe('IntroWindowComponent', () => {
  let component: IntroWindowComponent;
  let fixture: ComponentFixture<IntroWindowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IntroWindowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IntroWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
