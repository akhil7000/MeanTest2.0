//main class

import { Injectable } from '@angular/core';
import { UsersHttpService } from './users.http.service';
import 'rxjs/add/operator/map';

@Injectable()
export class UsersService {

  constructor(private usersHttp: UsersHttpService) { }

  getUsers() {
    return this.usersHttp.get().map(data => {
      return data.json();
    });
  }
}

//https service

import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

@Injectable()
export class UsersHttpService {

  constructor(private http: HTTP) { }

  get() {
	this.variable1=true;  
    return this.http.get('https://jsonplaceholder.typicode.com/users');
  }
}