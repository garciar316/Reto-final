import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';

import { AnswerI } from '../models/answer-i';
import { QuestionI } from '../models/question-i';
import { environment } from 'src/environments/environment';
import { QuestionService } from './question.service';

describe('QuestionService', () => {
  let service: QuestionService;
  let httpMock: HttpTestingController;
  let answer: AnswerI;
  let question: QuestionI;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [QuestionService],
    });
    service = TestBed.inject(QuestionService);
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

  it('getQuestion should GET and return data', () => {
    const id: string = '12345';
    service.getQuestion(id).subscribe((res) => {
      expect(res).toEqual(question);
    });

    const req = httpMock.expectOne(`${environment.baseUrl}get/${id}`);
    expect(req.request.method).toBe('GET');
    req.flush(question);
  });

  it('saveAnswer should POST and return data', () => {
    service.saveQuestion(question).subscribe((res) => {
      expect(res).toEqual(question);
    });

    const req = httpMock.expectOne(`${environment.baseUrl}create`);
    expect(req.request.method).toBe('POST');
  });

  it('editQuestion should put and return', () => {
    service.editQuestion(question).subscribe((res) => {
      expect(res).toEqual(question);
    });

    const req = httpMock.expectOne(`${environment.baseUrl}update`);
    expect(req.request.method).toBe('PUT');
    req.flush(question);
  });

it('deleteQuestion should DELETE and return void', () => {
    const id: string = '12345';
    service.deleteQuestion(id).subscribe((res) => {
      expect(res).toEqual();
    });

    const req = httpMock.expectOne(`${environment.baseUrl}delete/${id}`);
    expect(req.request.method).toBe('DELETE');
  });
});
