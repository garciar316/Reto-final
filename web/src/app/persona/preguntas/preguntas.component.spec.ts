import { ComponentFixture, TestBed } from '@angular/core/testing';
import { QuestionService } from 'src/app/Service/question.service';
import { ServiceService } from 'src/app/Service/service.service';

import { PreguntasComponent } from './preguntas.component';
import firebase from 'firebase/compat';
import {of} from 'rxjs';
import { QuestionI } from 'src/app/models/question-i';
import { AnswerI } from 'src/app/models/answer-i';

describe('PreguntasComponent', () => {

  let component: PreguntasComponent;
  let fixture: ComponentFixture<PreguntasComponent>;
  let authService: jasmine.SpyObj<ServiceService>;
  let questionService : jasmine.SpyObj<QuestionService>;
  let question : QuestionI;
  let answer: AnswerI;

  beforeEach(async () => {

    authService = jasmine.createSpyObj<ServiceService>
    ('ServiceService',[
      'getUserLogged'
    ]);

    questionService = jasmine.createSpyObj<QuestionService>
    ('QuestionService' , [
      'getAllQuestions',
      'deleteQuestion'
    ])


    await TestBed.configureTestingModule({
      declarations: [ PreguntasComponent ],
      imports: [],
      providers: [
        { provide: ServiceService, useValue: authService },
        { provide: QuestionService, useValue: questionService }
      ],
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PreguntasComponent);
    component = fixture.componentInstance;
    const user = {
      isAnonymous: true,
      uid: '17WvU2Vj58SnTz8v7EqyYYb0WRc2',
    } as firebase.User;

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
      answers: [
        answer
      ],
      start: '1',
    };
    component.userLogged = of(user);
    questionService.getAllQuestions.and.returnValue(of([question]))

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
