import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { WeatherJHipTestEntityModule } from './test-entity/test-entity.module';
import { WeatherJHipBlahModule } from './blah/blah.module';
import { WeatherJHipTemperatureModule } from './temperature/temperature.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        WeatherJHipTestEntityModule,
        WeatherJHipBlahModule,
        WeatherJHipTemperatureModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WeatherJHipEntityModule {}
