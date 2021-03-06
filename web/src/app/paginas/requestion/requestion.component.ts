import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AnswerI } from 'src/app/models/answer-i';
import { QuestionI } from 'src/app/models/question-i';
import { AnswerService } from 'src/app/Service/answer.service';
import { QuestionService } from 'src/app/Service/question.service';
import { ServiceService } from 'src/app/Service/service.service';

@Component({
  selector: 'app-requestion',
  templateUrl: './requestion.component.html',
  styleUrls: ['./requestion.component.css'],
})
export class RequestionComponent implements OnInit {
  userLogged = this.authService.getUserLogged();
  question: QuestionI | undefined;
  answers: AnswerI[] | undefined;
  answersNew: AnswerI[] = [];
  currentAnswer: number = 0;
  uid: any;

  questions: QuestionI[] | undefined;

  page: number = 0;

  constructor(
    private route: ActivatedRoute,
    private questionService: QuestionService,
    private answerService: AnswerService,
    public authService: ServiceService
  ) {}

  id: string | undefined;

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.getQuestions(`${id}`);
  }

  getQuestions(id: string): void {
    this.userLogged.subscribe((value) => {
      this.uid = value?.email;
    });
    this.questionService.getQuestion(id).subscribe((data) => {
      this.question = data;
      this.answers = data.answers?.sort((x: AnswerI, y: AnswerI) => {
        if (x.position < y.position) {
          return -1;
        }
        if (x.position > y.position) {
          return 1;
        }
        return 0;
      });
    });
  }

  AddAnwsers(index: number) {
    let last = this.currentAnswer + index;
    for (let i = this.currentAnswer; i < last; i++) {}
    this.currentAnswer += 10;
  }
}
