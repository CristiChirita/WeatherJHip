import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITemperature } from 'app/shared/model/temperature.model';

@Component({
    selector: 'jhi-temperature-detail',
    templateUrl: './temperature-detail.component.html'
})
export class TemperatureDetailComponent implements OnInit {
    temperature: ITemperature;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ temperature }) => {
            this.temperature = temperature;
        });
    }

    previousState() {
        window.history.back();
    }
}
