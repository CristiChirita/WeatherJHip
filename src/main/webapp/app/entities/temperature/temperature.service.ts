import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITemperature } from 'app/shared/model/temperature.model';

type EntityResponseType = HttpResponse<ITemperature>;
type EntityArrayResponseType = HttpResponse<ITemperature[]>;

@Injectable({ providedIn: 'root' })
export class TemperatureService {
    private resourceUrl = SERVER_API_URL + 'api/temperatures';

    constructor(private http: HttpClient) {}

    create(temperature: ITemperature): Observable<EntityResponseType> {
        return this.http.post<ITemperature>(this.resourceUrl, temperature, { observe: 'response' });
    }

    update(temperature: ITemperature): Observable<EntityResponseType> {
        return this.http.put<ITemperature>(this.resourceUrl, temperature, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITemperature>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITemperature[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
