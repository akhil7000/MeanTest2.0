System.register(["angular2/core", "angular2/common", "../../services/name_list"], function (exports_1, context_1) {
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
    var core_1, common_1, name_list_1, AboutCmp, _a;
    return {
        setters: [
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (common_1_1) {
                common_1 = common_1_1;
            },
            function (name_list_1_1) {
                name_list_1 = name_list_1_1;
            }
        ],
        execute: function () {
            AboutCmp = (function () {
                function AboutCmp(list) {
                    this.list = list;
                }
                AboutCmp.prototype.addName = function (newname) {
                    this.list.add(newname.value);
                    newname.value = '';
                    return false;
                };
                return AboutCmp;
            }());
            AboutCmp = __decorate([
                core_1.Component({
                    selector: 'about',
                    styles: [
                        "\n      ul li a {\n        color:blue;\n        cursor:pointer;\n      }\n    "
                    ],
                    templateUrl: './components/about/about.html',
                    directives: [common_1.NgFor]
                }),
                __metadata("design:paramtypes", [typeof (_a = typeof name_list_1.NameList !== "undefined" && name_list_1.NameList) === "function" && _a || Object])
            ], AboutCmp);
            exports_1("AboutCmp", AboutCmp);
        }
    };
});
