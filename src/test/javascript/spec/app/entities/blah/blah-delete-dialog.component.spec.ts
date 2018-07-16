/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WeatherJHipTestModule } from '../../../test.module';
import { BlahDeleteDialogComponent } from 'app/entities/blah/blah-delete-dialog.component';
import { BlahService } from 'app/entities/blah/blah.service';

describe('Component Tests', () => {
    describe('Blah Management Delete Component', () => {
        let comp: BlahDeleteDialogComponent;
        let fixture: ComponentFixture<BlahDeleteDialogComponent>;
        let service: BlahService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WeatherJHipTestModule],
                declarations: [BlahDeleteDialogComponent]
            })
                .overrideTemplate(BlahDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BlahDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BlahService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
