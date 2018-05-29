System.register(["@angular/core", "@angular/http", "../services/eir.callController", "../services/eir.getData", "@angular/router"], function (exports_1, context_1) {
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
    var core_1, http_1, eir_callController_1, eir_getData_1, router_1, EirCreateComponent, _a, _b, _c;
    return {
        setters: [
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (http_1_1) {
                http_1 = http_1_1;
            },
            function (eir_callController_1_1) {
                eir_callController_1 = eir_callController_1_1;
            },
            function (eir_getData_1_1) {
                eir_getData_1 = eir_getData_1_1;
            },
            function (router_1_1) {
                router_1 = router_1_1;
            }
        ],
        execute: function () {
            EirCreateComponent = (function () {
                function EirCreateComponent(_appService, _dataService, router) {
                    var _this = this;
                    this._appService = _appService;
                    this._dataService = _dataService;
                    this.router = router;
                    this.value = {};
                    this.data = {};
                    this.checkboxValue = {};
                    this.data.comboWithoutScore = false;
                    this.data.comboWithScore = false;
                    this.data.commWithScore = false;
                    this.data.commWithoutScore = false;
                    this.data.sme = false;
                    this.data.bir = false;
                    this.data.newsFeed = false;
                    this.data.litigation = false;
                    this._subscription = this._dataService.getData().subscribe(function (checkboxValue) {
                        console.log("Inside init method" + checkboxValue);
                        _this.jsonResponse = JSON.stringify(checkboxValue);
                        console.log("Message data......" + _this.messagess);
                        console.log("Json response:" + _this.jsonResponse);
                        _this.isCws = checkboxValue.comboWithScore;
                        _this.isCwos = checkboxValue.comboWithoutScore;
                        _this.isCIRws = checkboxValue.commWithScore;
                        _this.isCIRwos = checkboxValue.commWithoutScore;
                        _this.isSME = checkboxValue.sme;
                        _this.isBIR = checkboxValue.bir;
                        _this.isnf = checkboxValue.newsFeed;
                        _this.isld = checkboxValue.litigation;
                    }, function (err) { return console.log(err); }, function () { return console.log('hello service test complete'); });
                    this.value = this.data;
                }
                EirCreateComponent.prototype.back = function (check) {
                    this.router.navigate(['home']);
                };
                EirCreateComponent.prototype.selectedCheckBox = function () {
                    console.log("Hello......... from function");
                    if (this.data.comboWithoutScore || this.data.commWithoutScore || this.data.newsFeed ||
                        this.data.comboWithScore || this.data.commWithScore || this.data.litigation || this.data.sme || this.data.bir) {
                        if (this.data.bir && !(this.data.comboWithoutScore || this.data.commWithoutScore || this.data.newsFeed ||
                            this.data.comboWithScore || this.data.commWithScore || this.data.litigation || this.data.sme)) {
                            this.isOnlyBir = true;
                            console.log("Only bir:" + this.isOnlyBir);
                        }
                        if (this.isOnlyBir) {
                            this.router.navigate(['inputForEnquiry'], { queryParams: { isOnlyBir: true } });
                        }
                        else {
                            this.router.navigate(['inputForEnquiry'], { queryParams: { isOnlyBir: false } });
                        }
                    }
                    else {
                        alert("Select Product!!!");
                        this.router.navigate(['createEnquiry']);
                    }
                };
                return EirCreateComponent;
            }());
            __decorate([
                core_1.Input(),
                __metadata("design:type", Object)
            ], EirCreateComponent.prototype, "checkboxValue", void 0);
            EirCreateComponent = __decorate([
                core_1.Component({
                    selector: 'createEnquiry',
                    templateUrl: '../html/createEnquiry.html',
                    styleUrls: ['../css/bootstrap.css',
                        '../css/bootstrap.min.css',
                        '../css/bootstrap-theme.css',
                        '../css/bootstrap-theme.min.css',
                        '../css/carousel.css',
                        '../css/dashboard.css'],
                    providers: [http_1.HttpModule, eir_callController_1.AppService, eir_getData_1.DataService]
                }),
                __metadata("design:paramtypes", [typeof (_a = typeof eir_callController_1.AppService !== "undefined" && eir_callController_1.AppService) === "function" && _a || Object, typeof (_b = typeof eir_getData_1.DataService !== "undefined" && eir_getData_1.DataService) === "function" && _b || Object, typeof (_c = typeof router_1.Router !== "undefined" && router_1.Router) === "function" && _c || Object])
            ], EirCreateComponent);
            exports_1("EirCreateComponent", EirCreateComponent);
        }
    };
});
