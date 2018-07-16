import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { TestEntity } from 'app/shared/model/test-entity.model';
import { TestEntityService } from './test-entity.service';
import { TestEntityComponent } from './test-entity.component';
import { TestEntityDetailComponent } from './test-entity-detail.component';
import { TestEntityUpdateComponent } from './test-entity-update.component';
import { TestEntityDeletePopupComponent } from './test-entity-delete-dialog.component';
import { ITestEntity } from 'app/shared/model/test-entity.model';

@Injectable({ providedIn: 'root' })
export class TestEntityResolve implements Resolve<ITestEntity> {
    constructor(private service: TestEntityService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((testEntity: HttpResponse<TestEntity>) => testEntity.body));
        }
        return of(new TestEntity());
    }
}

export const testEntityRoute: Routes = [
    {
        path: 'test-entity',
        component: TestEntityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weatherJHipApp.testEntity.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'test-entity/:id/view',
        component: TestEntityDetailComponent,
        resolve: {
            testEntity: TestEntityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weatherJHipApp.testEntity.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'test-entity/new',
        component: TestEntityUpdateComponent,
        resolve: {
            testEntity: TestEntityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weatherJHipApp.testEntity.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'test-entity/:id/edit',
        component: TestEntityUpdateComponent,
        resolve: {
            testEntity: TestEntityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weatherJHipApp.testEntity.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const testEntityPopupRoute: Routes = [
    {
        path: 'test-entity/:id/delete',
        component: TestEntityDeletePopupComponent,
        resolve: {
            testEntity: TestEntityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weatherJHipApp.testEntity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
