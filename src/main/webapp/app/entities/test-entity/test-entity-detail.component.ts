import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITestEntity } from 'app/shared/model/test-entity.model';

@Component({
    selector: 'jhi-test-entity-detail',
    templateUrl: './test-entity-detail.component.html'
})
export class TestEntityDetailComponent implements OnInit {
    testEntity: ITestEntity;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ testEntity }) => {
            this.testEntity = testEntity;
        });
    }

    previousState() {
        window.history.back();
    }
}
