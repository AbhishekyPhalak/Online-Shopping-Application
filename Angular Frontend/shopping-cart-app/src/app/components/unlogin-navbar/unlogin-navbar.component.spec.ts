import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnloginNavbarComponent } from './unlogin-navbar.component';

describe('UnloginNavbarComponent', () => {
  let component: UnloginNavbarComponent;
  let fixture: ComponentFixture<UnloginNavbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnloginNavbarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnloginNavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
