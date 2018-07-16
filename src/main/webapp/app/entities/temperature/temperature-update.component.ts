import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITemperature } from 'app/shared/model/temperature.model';
import { TemperatureService } from './temperature.service';

@Component({
    selector: 'jhi-temperature-update',
    templateUrl: './temperature-update.component.html'
})
export class TemperatureUpdateComponent implements OnInit {
    private _temperature: ITemperature;
    isSaving: boolean;

    constructor(private temperatureService: TemperatureService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ temperature }) => {
            this.temperature = temperature;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.temperature.id !== undefined) {
            this.subscribeToSaveResponse(this.temperatureService.update(this.temperature));
        } else {
            this.subscribeToSaveResponse(this.temperatureService.create(this.temperature));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITemperature>>) {
        result.subscribe((res: HttpResponse<ITemperature>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get temperature() {
        return this._temperature;
    }

    set temperature(temperature: ITemperature) {
        this._temperature = temperature;
    }
}
