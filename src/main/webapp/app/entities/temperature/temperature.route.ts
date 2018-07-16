import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Temperature } from 'app/shared/model/temperature.model';
import { TemperatureService } from './temperature.service';
import { TemperatureComponent } from './temperature.component';
import { TemperatureDetailComponent } from './temperature-detail.component';
import { TemperatureUpdateComponent } from './temperature-update.component';
import { TemperatureDeletePopupComponent } from './temperature-delete-dialog.component';
import { ITemperature } from 'app/shared/model/temperature.model';

@Injectable({ providedIn: 'root' })
export class TemperatureResolve implements Resolve<ITemperature> {
    constructor(private service: TemperatureService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((temperature: HttpResponse<Temperature>) => temperature.body));
        }
        return of(new Temperature());
    }
}

export const temperatureRoute: Routes = [
    {
        path: 'temperature',
        component: TemperatureComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weatherJHipApp.temperature.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'temperature/:id/view',
        component: TemperatureDetailComponent,
        resolve: {
            temperature: TemperatureResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weatherJHipApp.temperature.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'temperature/new',
        component: TemperatureUpdateComponent,
        resolve: {
            temperature: TemperatureResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weatherJHipApp.temperature.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'temperature/:id/edit',
        component: TemperatureUpdateComponent,
        resolve: {
            temperature: TemperatureResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weatherJHipApp.temperature.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const temperaturePopupRoute: Routes = [
    {
        path: 'temperature/:id/delete',
        component: TemperatureDeletePopupComponent,
        resolve: {
            temperature: TemperatureResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weatherJHipApp.temperature.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
