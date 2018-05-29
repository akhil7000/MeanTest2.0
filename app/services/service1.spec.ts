
import { Injectable } from '@angular/core';
import { TestBed, async, inject } from '@angular/core/testing';
import { BaseRequestOptions, Response, ResponseOptions, Http } from '@angular/http';
import { MockBackend, MockConnection } from '@angular/http/testing';
import { getTestBed,  ComponentFixtureAutoDetect, ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { BrowserDynamicTestingModule,  platformBrowserDynamicTesting,} from '@angular/platform-browser-dynamic/testing';
import { HttpModule, XHRBackend, BaseRequestOptions, Response, ResponseOptions, Http } from '@angular/http';
import { MockBackend, MockConnection } from '@angular/http/testing';
import { By } from '@angular/platform-browser';

describe('Testing Engine', () => {
  let subject: Engine;
  beforeEach(() => {
  	      TestBed.configureTestingModule({
        providers: [Engine]
        ,imports: [//insertImports]
      });
    subject = new Engine();
  });


	describe('Test all methods on service', () => {

		it('Test if all methods are Defined',function()
		{
			expect(subject.getHorsepower()).toBeDefined();
			expect(subject.getName()).toBeDefined();
			expect(subject.get()).toBeDefined();
		});


  	  	
  	});
  	

		
  	describe('Test individual methods on service', () => {
  	
		xit('Test for getHorsepower()',function()
		{


			//make a method call
			getHorsepower();

		});

		xit('Test for getName()',function()
		{


			//make a method call
			getName();

		});

		xit('Test for get()',function()
		{
			//SET ONLY the value for selective variables from below that affect the method code
			var randomNames;

			//make a method call
			get();
			expect(get.randomNames).toEqual();

		});


  	
  	
  	});
	
  
  
});