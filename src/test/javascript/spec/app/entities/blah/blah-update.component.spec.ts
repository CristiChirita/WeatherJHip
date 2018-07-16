/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { WeatherJHipTestModule } from '../../../test.module';
import { BlahUpdateComponent } from 'app/entities/blah/blah-update.component';
import { BlahService } from 'app/entities/blah/blah.service';
import { Blah } from 'app/shared/model/blah.model';

describe('Component Tests', () => {
    describe('Blah Management Update Component', () => {
        let comp: BlahUpdateComponent;
        let fixture: ComponentFixture<BlahUpdateComponent>;
        let service: BlahService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WeatherJHipTestModule],
                declarations: [BlahUpdateComponent]
            })
                .overrideTemplate(BlahUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BlahUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BlahService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Blah(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.blah = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Blah();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.blah = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
