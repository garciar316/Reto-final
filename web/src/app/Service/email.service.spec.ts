import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { environment } from 'src/environments/environment';
import { EmailI } from '../models/email-i';

import { EmailService } from './email.service';

describe('EmailService', () => {
  let service: EmailService;
  let httpMock: HttpTestingController;
  let email: EmailI;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [EmailService],
    });
    service = TestBed.inject(EmailService);
    httpMock = TestBed.inject(HttpTestingController);
    email = {
      toEmail: 'correo@gmail.com',
      subject: 'saludo',
      body: 'Hola como estas',
    };
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('sendEmail should be POST and return', () => {
    service.sendEmail(email).subscribe((res) => {
      expect(res).toEqual('ok');
    });

    const req = httpMock.expectOne(`${environment.emailSenderUrl}`)
    expect(req.request.method).toBe('POST');
    req.flush('ok')
  })
});
