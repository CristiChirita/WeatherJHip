/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WeatherJHipTestModule } from '../../../test.module';
import { TestEntityComponent } from 'app/entities/test-entity/test-entity.component';
import { TestEntityService } from 'app/entities/test-entity/test-entity.service';
import { TestEntity } from 'app/shared/model/test-entity.model';

describe('Component Tests', () => {
    describe('TestEntity Management Component', () => {
        let comp: TestEntityComponent;
        let fixture: ComponentFixture<TestEntityComponent>;
        let service: TestEntityService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WeatherJHipTestModule],
                declarations: [TestEntityComponent],
                providers: []
            })
                .overrideTemplate(TestEntityComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TestEntityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TestEntityService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TestEntity(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.testEntities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
