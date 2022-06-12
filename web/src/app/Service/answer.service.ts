import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { QuestionI } from '../models/question-i';
import { AnswerI } from '../models/answer-i';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AnswerService {

  push(arg0: string) {
    throw new Error('Method not implemented.');
  }

  private url: string = environment.baseUrl;

  constructor(private http: HttpClient) {}

  getAnswer(id: any): Observable<QuestionI> {
    let direction = `${this.url}get/${id}`;
    return this.http.get<QuestionI>(direction);
  }

  saveAnswer(answer: AnswerI): Observable<QuestionI> {
    let direction = `${this.url}add`;
    return this.http.post<QuestionI>(direction, answer);
  }

  updateAnswer(answer: AnswerI): Observable<AnswerI> {
    let direction = `${this.url}updateAnswer`;
    return this.http.put<AnswerI>(direction, answer);
  }

}
