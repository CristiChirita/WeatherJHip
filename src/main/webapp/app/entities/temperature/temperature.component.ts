import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITemperature } from 'app/shared/model/temperature.model';
import { Principal } from 'app/core';
import { TemperatureService } from './temperature.service';

@Component({
    selector: 'jhi-temperature',
    templateUrl: './temperature.component.html'
})
export class TemperatureComponent implements OnInit, OnDestroy {
    temperatures: ITemperature[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private temperatureService: TemperatureService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

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
