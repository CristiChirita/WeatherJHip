import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WeatherJHipSharedModule } from 'app/shared';
import {
    TestEntityComponent,
    TestEntityDetailComponent,
    TestEntityUpdateComponent,
    TestEntityDeletePopupComponent,
    TestEntityDeleteDialogComponent,
    testEntityRoute,
    testEntityPopupRoute
} from './';

const ENTITY_STATES = [...testEntityRoute, ...testEntityPopupRoute];

@NgModule({
    imports: [WeatherJHipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TestEntityComponent,
        TestEntityDetailComponent,
        TestEntityUpdateComponent,
        TestEntityDeleteDialogComponent,
        TestEntityDeletePopupComponent
    ],
    entryComponents: [TestEntityComponent, TestEntityUpdateComponent, TestEntityDeleteDialogComponent, TestEntityDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WeatherJHipTestEntityModule {}
