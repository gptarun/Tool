import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OutcomeLearningCatalogComponent } from './outcome-learning-catalog.component';

describe('OutcomeLearningCatalogComponent', () => {
  let component: OutcomeLearningCatalogComponent;
  let fixture: ComponentFixture<OutcomeLearningCatalogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OutcomeLearningCatalogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OutcomeLearningCatalogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
