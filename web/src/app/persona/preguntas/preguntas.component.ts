import { Component, OnInit } from '@angular/core';
import * as e from 'express';
import { QuestionI } from 'src/app/models/question-i';
import { QuestionService } from 'src/app/Service/question.service';
import { ServiceService } from 'src/app/Service/service.service';

@Component({
  selector: 'app-preguntas',
  templateUrl: './preguntas.component.html',
  styleUrls: ['./preguntas.component.css'],
})
export class PreguntasComponent implements OnInit {
  userLogged = this.authService.getUserLogged();
  uid: any;

  questions: QuestionI[] | undefined;
  user: any = '';
  disabled: boolean = false;

  constructor(
    private service: QuestionService,
    public authService: ServiceService
  ) {}

  ngOnInit(): void {
    this.getQuestions();
    this.traerdatos();
  }

  getQuestions(): void {
    this.userLogged.subscribe((value) => {
      this.uid = value?.email;
    });
    this.service.getAllQuestions().subscribe({
      next: (value) => (this.questions = value),
      error: (error) => console.error(error),
    });
  }

  traerdatos() {
    this.userLogged.subscribe((value) => {
      if (value?.email == undefined) {
        this.disabled = true;
      } else {
        this.disabled = false;
      }
    });
  }

  delete(id?: string): void {
    if (id) {
      this.service.deleteQuestion(id).subscribe({
        next: (value) => console.log(value),
        error: (error) => console.error(error),
      });
      setTimeout(() => {
        window.location.reload();
      }, 2000);
    }
  }
}
