import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBlah } from 'app/shared/model/blah.model';
import { Principal } from 'app/core';
import { BlahService } from './blah.service';

@Component({
    selector: 'jhi-blah',
    templateUrl: './blah.component.html'
})
export class BlahComponent implements OnInit, OnDestroy {
    blahs: IBlah[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private blahService: BlahService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.blahService.query().subscribe(
            (res: HttpResponse<IBlah[]>) => {
                this.blahs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBlahs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBlah) {
        return item.id;
    }

    registerChangeInBlahs() {
        this.eventSubscriber = this.eventManager.subscribe('blahListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
