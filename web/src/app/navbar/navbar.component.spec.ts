import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { ServiceService } from '../Service/service.service';

import { NavbarComponent } from './navbar.component';
import firebase from 'firebase/compat';
import {of} from 'rxjs'

describe('NavbarComponent', () => {

  let component: NavbarComponent;
  let fixture: ComponentFixture<NavbarComponent>;
  let authService: jasmine.SpyObj<ServiceService>;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(async () => {

    authService = jasmine.createSpyObj<ServiceService>
    ('ServiceService',[
      'getUserLogged',
      'logout'
    ]);

    routerSpy = jasmine.createSpyObj<Router>
    ('Router',[
      'navigate'
    ])

    await TestBed.configureTestingModule({
      declarations: [ NavbarComponent ],
      imports: [],
      providers: [
        { provide: ServiceService, useValue: authService },
        { provide: Router, useValue: routerSpy }
      ],
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NavbarComponent);
    component = fixture.componentInstance;
    const user = {
      isAnonymous: true,
      uid: '17WvU2Vj58SnTz8v7EqyYYb0WRc2',
    } as firebase.User;
    component.userLogged = of(user);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
