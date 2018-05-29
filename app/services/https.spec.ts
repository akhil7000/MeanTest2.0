
import { Injectable } from '@angular/core';
import { UsersHttpService } from './users.http.service';
import 'rxjs/add/operator/map';
import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { TestBed, async, inject } from '@angular/core/testing';
import { BaseRequestOptions, Response, ResponseOptions, Http } from '@angular/http';
import { MockBackend, MockConnection } from '@angular/http/testing';
import { getTestBed,  ComponentFixtureAutoDetect, ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { BrowserDynamicTestingModule,  platformBrowserDynamicTesting,} from '@angular/platform-browser-dynamic/testing';
import { HttpModule, XHRBackend, BaseRequestOptions, Response, ResponseOptions, Http } from '@angular/http';
import { MockBackend, MockConnection } from '@angular/http/testing';
import { By } from '@angular/platform-browser';

describe('Testing UsersHttpService', () => {
  let subject: UsersHttpService;
  beforeEach(() => {
  	      TestBed.configureTestingModule({
        providers: [UsersHttpService,UsersHttpService]
        ,imports: [//insertImports]
      });
    subject = new UsersHttpService();
  });


	describe('Test all methods on service', () => {

		it('Test if all methods are Defined',function()
		{
			expect(subject.get()).toBeDefined();
		});


  	  	
  	});
  	

		
  	describe('Test individual methods on service', () => {
  	
		xit('Test for get()',function()
		{
			//SET ONLY the value for selective variables from below that affect the method code
			var variable1;

			//make a method call
			get();
			expect(get.variable1).toEqual();

		});


  	
  	
  	});
	
  
  
});