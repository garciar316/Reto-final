import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';

import { AnswerService } from './answer.service';
import { AnswerI } from '../models/answer-i';
import { QuestionI } from '../models/question-i';
import { environment } from 'src/environments/environment';

describe('AnswerService', () => {
  let service: AnswerService;
  let httpMock: HttpTestingController;
  let answer: AnswerI;
  let question: QuestionI;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AnswerService],
    });
    service = TestBed.inject(AnswerService);
    httpMock = TestBed.inject(HttpTestingController);
    answer = {
      id: '1234',
      userId: '123',
      questionId: '12345',
      answer: 'respuesta',
      position: 1,
    };
    question = {
      id: '12345',
      userId: '123',
      question: 'pregunta',
      type: 'abierta',
      category: 'dificil',
      answers: [answer],
      start: '1',
    };
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('getAnswer return data', () => {
    const id = '12345';
    service.getAnswer(id).subscribe((res) => {
      expect(res).toEqual(question);
    });

    const req = httpMock.expectOne(`${environment.baseUrl}get/${id}`);
    expect(req.request.method).toBe('GET');
    req.flush(question);
  });

  it('saveAnswer should POST and return data', () => {
    service.saveAnswer(answer).subscribe((res) => {
      expect(res).toEqual(question);
    });

    const req = httpMock.expectOne(`${environment.baseUrl}add`);
    expect(req.request.method).toBe('POST');
    req.flush(question);
  });

  it('update should put and return', () => {
    service.updateAnswer(answer).subscribe((res) => {
      expect(res).toEqual(answer);
    });

    const req = httpMock.expectOne(`${environment.baseUrl}updateAnswer`);
    expect(req.request.method).toBe('PUT');
    req.flush(answer);
  });
});
