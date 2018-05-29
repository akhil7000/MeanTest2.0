System.register(["@angular/core", "./users.http.service", "rxjs/add/operator/map"], function (exports_1, context_1) {
    "use strict";
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var __moduleName = context_1 && context_1.id;
    var core_1, users_http_service_1, UsersService, UsersHttpService, _a;
    return {
        setters: [
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (users_http_service_1_1) {
                users_http_service_1 = users_http_service_1_1;
            },
            function (_1) {
            }
        ],
        execute: function () {
            exports_1("UsersHttpService", users_http_service_1.UsersHttpService);
            UsersService = (function () {
                function UsersService(usersHttp) {
                    this.usersHttp = usersHttp;
                }
                UsersService.prototype.getUsers = function () {
                    return this.usersHttp.get().map(function (data) {
                        return data.json();
                    });
                };
                return UsersService;
            }());
            UsersService = __decorate([
                core_1.Injectable(),
                __metadata("design:paramtypes", [typeof (_a = typeof users_http_service_1.UsersHttpService !== "undefined" && users_http_service_1.UsersHttpService) === "function" && _a || Object])
            ], UsersService);
            exports_1("UsersService", UsersService);
            UsersHttpService = (function () {
                function UsersHttpService(http) {
                    this.http = http;
                }
                UsersHttpService.prototype.get = function () {
                    this.variable1 = true;
                    return this.http.get('https://jsonplaceholder.typicode.com/users');
                };
                return UsersHttpService;
            }());
            UsersHttpService = __decorate([
                core_1.Injectable(),
                __metadata("design:paramtypes", [Object])
            ], UsersHttpService);
            exports_1("UsersHttpService", UsersHttpService);
        }
    };
});
