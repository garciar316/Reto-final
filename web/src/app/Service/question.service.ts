import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { QuestionI } from '../models/question-i';
import { AnswerI } from '../models/answer-i';
import { environment } from 'src/environments/environment';
@Injectable({
  providedIn: 'root',
})
export class QuestionService {
  push(arg0: string) {
    throw new Error('Method not implemented.');
  }

  private url: string = environment.baseUrl;

  constructor(private http: HttpClient) {}

  getQuestion(id: string): Observable<QuestionI> {
    let direction = `${this.url}get/${id}`;
    return this.http.get<QuestionI>(direction);
  }

  getAllQuestions(): Observable<QuestionI[]> {
    let direction = `${this.url}getAll`;
    return this.http.get<QuestionI[]>(direction);
  }

  saveQuestion(question: QuestionI): Observable<QuestionI> {
    let direction = `${this.url}create`;
    return this.http.post<QuestionI>(direction, question, {
      responseType: 'text' as 'json',
    });
  }

  editQuestion(question: QuestionI): Observable<QuestionI> {
    let direction = `${this.url}update`;
    return this.http.put<QuestionI>(direction, question);
  }

  deleteQuestion(id: string): Observable<void> {
    let direction = `${this.url}delete/${id}`;
    return this.http.delete<void>(direction);
  }
}
