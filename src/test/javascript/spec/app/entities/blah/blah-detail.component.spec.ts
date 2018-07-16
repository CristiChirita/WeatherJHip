/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WeatherJHipTestModule } from '../../../test.module';
import { BlahDetailComponent } from 'app/entities/blah/blah-detail.component';
import { Blah } from 'app/shared/model/blah.model';

describe('Component Tests', () => {
    describe('Blah Management Detail Component', () => {
        let comp: BlahDetailComponent;
        let fixture: ComponentFixture<BlahDetailComponent>;
        const route = ({ data: of({ blah: new Blah(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WeatherJHipTestModule],
                declarations: [BlahDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BlahDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BlahDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.blah).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
