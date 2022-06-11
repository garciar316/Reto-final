import { Injectable } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/compat/auth';
import {
  AngularFirestore,
  AngularFirestoreDocument,
} from '@angular/fire/compat/firestore';
import { Router } from '@angular/router';

import firebase from 'firebase/compat/app';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class ServiceService {
  constructor(
    public afauth: AngularFireAuth,
    public store: AngularFirestore,
    public router: Router
  ) {
    this.afauth.authState.subscribe((user) => {
      if (user) {
        JSON.parse(localStorage.getItem('user')!);
      } else {
        JSON.parse(localStorage.getItem('user')!);
        localStorage.setItem('user', 'null');
      }
    });
  }

  async login(email: string, password: string) {
    try {
      return await this.afauth.signInWithEmailAndPassword(email, password);
    } catch (error) {
      return null;
    }
  }
  async loginRegistre(email: string, password: string) {
    try {
      return await this.afauth.createUserWithEmailAndPassword(email, password);
    } catch (error) {
      return null;
    }
  }

  async resetPassword(email: string) {
    try {
      return this.afauth.sendPasswordResetEmail(email);
    } catch (error) {
      return null;
    }
  }
  async loginGoogle(email: string, password: string) {
    try {
      return await this.afauth.signInWithPopup(
        new firebase.auth.GoogleAuthProvider()
      );
    } catch (error) {
      return null;
    }
  }

  getUserLogged() {
    return this.afauth.authState;
  }

  logout() {
    this.afauth.signOut();
  }
}
