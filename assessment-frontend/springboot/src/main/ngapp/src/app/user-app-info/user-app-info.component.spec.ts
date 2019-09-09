import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { UserAppInfoComponent } from './user-app-info.component';

describe('UserAppInfoComponent', () => {
  let component: UserAppInfoComponent;
  let fixture: ComponentFixture<UserAppInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserAppInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserAppInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
