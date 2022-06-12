import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { EmailI } from '../models/email-i';

@Injectable({
  providedIn: 'root',
})
export class EmailService {
  private url = environment.emailSenderUrl;
  constructor(private http: HttpClient) {}

  sendEmail(email: EmailI): Observable<string> {
    return this.http.post<string>(this.url, email);
  }
}
