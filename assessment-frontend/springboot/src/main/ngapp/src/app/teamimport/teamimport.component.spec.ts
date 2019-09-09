import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamimportComponent } from './teamimport.component';

describe('TeamimportComponent', () => {
  let component: TeamimportComponent;
  let fixture: ComponentFixture<TeamimportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TeamimportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TeamimportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
