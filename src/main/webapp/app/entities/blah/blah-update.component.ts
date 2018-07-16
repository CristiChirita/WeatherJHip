import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IBlah } from 'app/shared/model/blah.model';
import { BlahService } from './blah.service';

@Component({
    selector: 'jhi-blah-update',
    templateUrl: './blah-update.component.html'
})
export class BlahUpdateComponent implements OnInit {
    private _blah: IBlah;
    isSaving: boolean;

    constructor(private blahService: BlahService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ blah }) => {
            this.blah = blah;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.blah.id !== undefined) {
            this.subscribeToSaveResponse(this.blahService.update(this.blah));
        } else {
            this.subscribeToSaveResponse(this.blahService.create(this.blah));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBlah>>) {
        result.subscribe((res: HttpResponse<IBlah>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get blah() {
        return this._blah;
    }

    set blah(blah: IBlah) {
        this._blah = blah;
    }
}
