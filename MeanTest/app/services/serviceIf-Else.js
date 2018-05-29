System.register(["@angular/core"], function (exports_1, context_1) {
    "use strict";
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __moduleName = context_1 && context_1.id;
    var core_1, PEOPLE, PeopleService;
    return {
        setters: [
            function (core_1_1) {
                core_1 = core_1_1;
            }
        ],
        execute: function () {
            PEOPLE = [
                { id: 1, name: 'Luke Skywalker', height: 177, weight: 70, profession: '' },
                { id: 2, name: 'Darth Vader', height: 200, weight: 100, profession: '' },
                { id: 3, name: 'Han Solo', height: 185, weight: 85, profession: '' },
            ];
            PeopleService = (function () {
                function PeopleService() {
                }
                PeopleService.prototype.getAll = function () {
                    var _this = this;
                    return PEOPLE.map(function (p) { return _this.clone(p); });
                };
                PeopleService.prototype.get = function (id) {
                    return this.clone(PEOPLE.find(function (p) { return p.id === id; }));
                };
                PeopleService.prototype.save = function (person) {
                    var originalPerson = PEOPLE.find(function (p) { return p.id === person.id; });
                    if (originalPerson)
                        Object.assign(originalPerson, person);
                };
                PeopleService.prototype.clone = function (object) {
                    return JSON.parse(JSON.stringify(object));
                };
                return PeopleService;
            }());
            PeopleService = __decorate([
                core_1.Injectable()
            ], PeopleService);
            exports_1("PeopleService", PeopleService);
        }
    };
});
