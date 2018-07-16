import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WeatherJHipSharedModule } from 'app/shared';
import {
    BlahComponent,
    BlahDetailComponent,
    BlahUpdateComponent,
    BlahDeletePopupComponent,
    BlahDeleteDialogComponent,
    blahRoute,
    blahPopupRoute
} from './';

const ENTITY_STATES = [...blahRoute, ...blahPopupRoute];

@NgModule({
    imports: [WeatherJHipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [BlahComponent, BlahDetailComponent, BlahUpdateComponent, BlahDeleteDialogComponent, BlahDeletePopupComponent],
    entryComponents: [BlahComponent, BlahUpdateComponent, BlahDeleteDialogComponent, BlahDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WeatherJHipBlahModule {}
