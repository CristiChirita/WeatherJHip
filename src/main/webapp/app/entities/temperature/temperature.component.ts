import {Component, OnInit, OnDestroy} from '@angular/core';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {Subscription} from 'rxjs';
import {JhiEventManager, JhiAlertService} from 'ng-jhipster';

import {ITemperature} from 'app/shared/model/temperature.model';
import {Principal} from 'app/core';
import {TemperatureService} from './temperature.service';

@Component({
    selector: 'jhi-temperature',
    templateUrl: './temperature.component.html'
})
export class TemperatureComponent implements OnInit, OnDestroy {
    temperatures: ITemperature[];
    currentAccount: any;
    eventSubscriber: Subscription;
    continentMod: number;
    minTempMod: number;
    cityMod: number;
    countryMod: number;
    maxTempMod: number;
    cityMaxMod: number;
    countryMaxMod: number;

    constructor(
        private temperatureService: TemperatureService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.temperatureService.query().subscribe(
            (res: HttpResponse<ITemperature[]>) => {
                this.temperatures = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTemperatures();
        this.continentMod = -1;
        this.minTempMod = -1;
        this.cityMod = -1;
        this.countryMod = -1;
        this.maxTempMod = -1;
        this.cityMaxMod = -1;
        this.countryMaxMod = -1;
    }

    sort(columnName: string) {
        if ('continent' === columnName) {
            this.continentMod *= -1;
            this.temperatures.sort((a, b) => {
                return a.continent.localeCompare(b.continent) * this.continentMod;
            });
        }
        if ('minTemperature' === columnName) {
            this.minTempMod *= -1;
            this.temperatures.sort((a, b) => {
                return (a.minTemperature - b.minTemperature) * this.minTempMod;
            });
        }
        if ('city' === columnName) {
            this.cityMod *= -1;
            this.temperatures.sort((a, b) => {
                return a.city.localeCompare(b.city) * this.cityMod;
            });
        }
        if ('country' === columnName) {
            this.countryMod *= -1;
            this.temperatures.sort((a, b) => {
                return a.country.localeCompare(b.country) * this.countryMod;
            });
        }
        if ('maxTemperature' === columnName) {
            this.maxTempMod *= -1;
            this.temperatures.sort((a, b) => {
                return (a.maxTemperature - b.maxTemperature) * this.maxTempMod;
            });
        }
        if ('cityMax' === columnName) {
            this.cityMaxMod *= -1;
            this.temperatures.sort((a, b) => {
                return a.cityMax.localeCompare(b.cityMax) * this.cityMaxMod;
            });
        }
        if ('countryMax' === columnName) {
            this.countryMaxMod *= -1;
            this.temperatures.sort((a, b) => {
                return a.countryMax.localeCompare(b.countryMax) * this.countryMaxMod;
            });
        }
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITemperature) {
        return item.id;
    }

    registerChangeInTemperatures() {
        this.eventSubscriber = this.eventManager.subscribe('temperatureListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
