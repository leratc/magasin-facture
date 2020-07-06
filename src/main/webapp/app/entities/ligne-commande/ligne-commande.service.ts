import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILigneCommande } from 'app/shared/model/ligne-commande.model';

type EntityResponseType = HttpResponse<ILigneCommande>;
type EntityArrayResponseType = HttpResponse<ILigneCommande[]>;

@Injectable({ providedIn: 'root' })
export class LigneCommandeService {
  public resourceUrl = SERVER_API_URL + 'api/ligne-commandes';

  constructor(protected http: HttpClient) {}

  create(ligneCommande: ILigneCommande): Observable<EntityResponseType> {
    return this.http.post<ILigneCommande>(this.resourceUrl, ligneCommande, { observe: 'response' });
  }

  update(ligneCommande: ILigneCommande): Observable<EntityResponseType> {
    return this.http.put<ILigneCommande>(this.resourceUrl, ligneCommande, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILigneCommande>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILigneCommande[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
