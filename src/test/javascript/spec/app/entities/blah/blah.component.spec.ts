/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WeatherJHipTestModule } from '../../../test.module';
import { BlahComponent } from 'app/entities/blah/blah.component';
import { BlahService } from 'app/entities/blah/blah.service';
import { Blah } from 'app/shared/model/blah.model';

describe('Component Tests', () => {
    describe('Blah Management Component', () => {
        let comp: BlahComponent;
        let fixture: ComponentFixture<BlahComponent>;
        let service: BlahService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WeatherJHipTestModule],
                declarations: [BlahComponent],
                providers: []
            })
                .overrideTemplate(BlahComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BlahComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BlahService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Blah(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.blahs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
