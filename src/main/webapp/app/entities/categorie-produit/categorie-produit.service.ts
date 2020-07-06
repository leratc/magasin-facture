import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICategorieProduit } from 'app/shared/model/categorie-produit.model';

type EntityResponseType = HttpResponse<ICategorieProduit>;
type EntityArrayResponseType = HttpResponse<ICategorieProduit[]>;

@Injectable({ providedIn: 'root' })
export class CategorieProduitService {
  public resourceUrl = SERVER_API_URL + 'api/categorie-produits';

  constructor(protected http: HttpClient) {}

  create(categorieProduit: ICategorieProduit): Observable<EntityResponseType> {
    return this.http.post<ICategorieProduit>(this.resourceUrl, categorieProduit, { observe: 'response' });
  }

  update(categorieProduit: ICategorieProduit): Observable<EntityResponseType> {
    return this.http.put<ICategorieProduit>(this.resourceUrl, categorieProduit, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICategorieProduit>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategorieProduit[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
