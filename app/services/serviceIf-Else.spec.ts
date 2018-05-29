
import { Injectable } from '@angular/core';
import { Person } from './person';
import { TestBed, async, inject } from '@angular/core/testing';
import { BaseRequestOptions, Response, ResponseOptions, Http } from '@angular/http';
import { MockBackend, MockConnection } from '@angular/http/testing';
import { getTestBed,  ComponentFixtureAutoDetect, ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { BrowserDynamicTestingModule,  platformBrowserDynamicTesting,} from '@angular/platform-browser-dynamic/testing';
import { HttpModule, XHRBackend, BaseRequestOptions, Response, ResponseOptions, Http } from '@angular/http';
import { MockBackend, MockConnection } from '@angular/http/testing';
import { By } from '@angular/platform-browser';

describe('Testing PeopleService', () => {
  let subject: PeopleService;
  beforeEach(() => {
  	      TestBed.configureTestingModule({
        providers: [PeopleService,Person]
        ,imports: [//insertImports]
      });
    subject = new PeopleService();
  });


	describe('Test all methods on service', () => {

		it('Test if all methods are Defined',function()
		{
			expect(subject.getAll()).toBeDefined();
			expect(subject.get()).toBeDefined();
			expect(subject.save()).toBeDefined();
			expect(subject.clone()).toBeDefined();
		});


  	  	
  	});
  	

		
  	describe('Test individual methods on service', () => {
  	
		xit('Test for getAll()',function()
		{


			//make a method call
			getAll();

		});

		xit('Test for get()',function()
		{

			//set value for paramaters of method
			var id;

			//make a method call
			get(id);

		});

		xit('Test for save()',function()
		{

			//set value for paramaters of method
			var person;

			//make a method call
			save(person);

			//repeat required steps for coverage of following 'if' conditions :
			// 1 :  (originalPerson)
		});

		xit('Test for clone()',function()
		{

			//set value for paramaters of method
			var object;

			//make a method call
			clone(object);

		});


  	
  	
  	});
	
  
  
});