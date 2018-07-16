import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WeatherJHipSharedModule } from 'app/shared';
import {
    TemperatureComponent,
    TemperatureDetailComponent,
    TemperatureUpdateComponent,
    TemperatureDeletePopupComponent,
    TemperatureDeleteDialogComponent,
    temperatureRoute,
    temperaturePopupRoute
} from './';

const ENTITY_STATES = [...temperatureRoute, ...temperaturePopupRoute];

@NgModule({
    imports: [WeatherJHipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TemperatureComponent,
        TemperatureDetailComponent,
        TemperatureUpdateComponent,
        TemperatureDeleteDialogComponent,
        TemperatureDeletePopupComponent
    ],
    entryComponents: [TemperatureComponent, TemperatureUpdateComponent, TemperatureDeleteDialogComponent, TemperatureDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WeatherJHipTemperatureModule {}
