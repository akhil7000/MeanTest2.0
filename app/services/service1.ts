//service with 2 return methods

import { Injectable } from '@angular/core';
@Injectable()
export class Engine {
	  randomNames = ['Dijkstra', 'Knuth', 'Turing', 'Hopper'];

  get(): string[] {
    return this.randomNames;
  }
	
	
  getHorsepower() {
    return 150;
  }
  getName() {
    return 'Basic engine';
  }
}

