import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Blah } from 'app/shared/model/blah.model';
import { BlahService } from './blah.service';
import { BlahComponent } from './blah.component';
import { BlahDetailComponent } from './blah-detail.component';
import { BlahUpdateComponent } from './blah-update.component';
import { BlahDeletePopupComponent } from './blah-delete-dialog.component';
import { IBlah } from 'app/shared/model/blah.model';

@Injectable({ providedIn: 'root' })
export class BlahResolve implements Resolve<IBlah> {
    constructor(private service: BlahService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((blah: HttpResponse<Blah>) => blah.body));
        }
        return of(new Blah());
    }
}

export const blahRoute: Routes = [
    {
        path: 'blah',
        component: BlahComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weatherJHipApp.blah.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'blah/:id/view',
        component: BlahDetailComponent,
        resolve: {
            blah: BlahResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weatherJHipApp.blah.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'blah/new',
        component: BlahUpdateComponent,
        resolve: {
            blah: BlahResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weatherJHipApp.blah.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'blah/:id/edit',
        component: BlahUpdateComponent,
        resolve: {
            blah: BlahResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weatherJHipApp.blah.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const blahPopupRoute: Routes = [
    {
        path: 'blah/:id/delete',
        component: BlahDeletePopupComponent,
        resolve: {
            blah: BlahResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weatherJHipApp.blah.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
