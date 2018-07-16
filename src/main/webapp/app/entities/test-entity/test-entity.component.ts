import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITestEntity } from 'app/shared/model/test-entity.model';
import { Principal } from 'app/core';
import { TestEntityService } from './test-entity.service';

@Component({
    selector: 'jhi-test-entity',
    templateUrl: './test-entity.component.html'
})
export class TestEntityComponent implements OnInit, OnDestroy {
    testEntities: ITestEntity[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private testEntityService: TestEntityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.testEntityService.query().subscribe(
            (res: HttpResponse<ITestEntity[]>) => {
                this.testEntities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTestEntities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITestEntity) {
        return item.id;
    }

    registerChangeInTestEntities() {
        this.eventSubscriber = this.eventManager.subscribe('testEntityListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
