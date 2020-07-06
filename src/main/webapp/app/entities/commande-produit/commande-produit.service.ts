import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICommandeProduit } from 'app/shared/model/commande-produit.model';

type EntityResponseType = HttpResponse<ICommandeProduit>;
type EntityArrayResponseType = HttpResponse<ICommandeProduit[]>;

@Injectable({ providedIn: 'root' })
export class CommandeProduitService {
  public resourceUrl = SERVER_API_URL + 'api/commande-produits';

  constructor(protected http: HttpClient) {}

  create(commandeProduit: ICommandeProduit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commandeProduit);
    return this.http
      .post<ICommandeProduit>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(commandeProduit: ICommandeProduit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commandeProduit);
    return this.http
      .put<ICommandeProduit>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICommandeProduit>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICommandeProduit[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(commandeProduit: ICommandeProduit): ICommandeProduit {
    const copy: ICommandeProduit = Object.assign({}, commandeProduit, {
      dateCommande:
        commandeProduit.dateCommande && commandeProduit.dateCommande.isValid() ? commandeProduit.dateCommande.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateCommande = res.body.dateCommande ? moment(res.body.dateCommande) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((commandeProduit: ICommandeProduit) => {
        commandeProduit.dateCommande = commandeProduit.dateCommande ? moment(commandeProduit.dateCommande) : undefined;
      });
    }
    return res;
  }
}
