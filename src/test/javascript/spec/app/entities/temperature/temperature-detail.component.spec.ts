/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WeatherJHipTestModule } from '../../../test.module';
import { TemperatureDetailComponent } from 'app/entities/temperature/temperature-detail.component';
import { Temperature } from 'app/shared/model/temperature.model';

describe('Component Tests', () => {
    describe('Temperature Management Detail Component', () => {
        let comp: TemperatureDetailComponent;
        let fixture: ComponentFixture<TemperatureDetailComponent>;
        const route = ({ data: of({ temperature: new Temperature(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WeatherJHipTestModule],
                declarations: [TemperatureDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TemperatureDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TemperatureDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.temperature).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
