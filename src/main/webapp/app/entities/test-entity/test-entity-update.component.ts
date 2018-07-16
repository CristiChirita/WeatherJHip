import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITestEntity } from 'app/shared/model/test-entity.model';
import { TestEntityService } from './test-entity.service';

@Component({
    selector: 'jhi-test-entity-update',
    templateUrl: './test-entity-update.component.html'
})
export class TestEntityUpdateComponent implements OnInit {
    private _testEntity: ITestEntity;
    isSaving: boolean;

    constructor(private testEntityService: TestEntityService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ testEntity }) => {
            this.testEntity = testEntity;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.testEntity.id !== undefined) {
            this.subscribeToSaveResponse(this.testEntityService.update(this.testEntity));
        } else {
            this.subscribeToSaveResponse(this.testEntityService.create(this.testEntity));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITestEntity>>) {
        result.subscribe((res: HttpResponse<ITestEntity>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get testEntity() {
        return this._testEntity;
    }

    set testEntity(testEntity: ITestEntity) {
        this._testEntity = testEntity;
    }
}
