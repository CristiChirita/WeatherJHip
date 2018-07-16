import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBlah } from 'app/shared/model/blah.model';

type EntityResponseType = HttpResponse<IBlah>;
type EntityArrayResponseType = HttpResponse<IBlah[]>;

@Injectable({ providedIn: 'root' })
export class BlahService {
    private resourceUrl = SERVER_API_URL + 'api/blahs';

    constructor(private http: HttpClient) {}

    create(blah: IBlah): Observable<EntityResponseType> {
        return this.http.post<IBlah>(this.resourceUrl, blah, { observe: 'response' });
    }

    update(blah: IBlah): Observable<EntityResponseType> {
        return this.http.put<IBlah>(this.resourceUrl, blah, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBlah>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBlah[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
