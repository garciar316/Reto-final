import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { ServiceService } from 'src/app/Service/service.service';

import { LoginComponent } from './login.component';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let spyMessageService: jasmine.SpyObj<MessageService>;
  let spyAuthService: jasmine.SpyObj<ServiceService>;
  let routerSpy: jasmine.SpyObj<Router>;
  beforeEach(async () => {
    spyMessageService = jasmine.createSpyObj<MessageService>('MessageService', [
      'add',
    ]);
    spyAuthService = jasmine.createSpyObj<ServiceService>('ServiceService', [
      'login',
      'loginGoogle',
      'getUserLogged',
      'resetPassword',
    ]);
    routerSpy = jasmine.createSpyObj<Router>('Router', ['navigate']);
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      imports: [ReactiveFormsModule, FormsModule],
      providers: [
        { provide: MessageService, useValue: spyMessageService },
        { provide: ServiceService, useValue: spyAuthService },
        { provide: Router, useValue: routerSpy }
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
