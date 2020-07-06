import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICategorieProduit, CategorieProduit } from 'app/shared/model/categorie-produit.model';
import { CategorieProduitService } from './categorie-produit.service';
import { CategorieProduitComponent } from './categorie-produit.component';
import { CategorieProduitDetailComponent } from './categorie-produit-detail.component';
import { CategorieProduitUpdateComponent } from './categorie-produit-update.component';

@Injectable({ providedIn: 'root' })
export class CategorieProduitResolve implements Resolve<ICategorieProduit> {
  constructor(private service: CategorieProduitService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategorieProduit> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((categorieProduit: HttpResponse<CategorieProduit>) => {
          if (categorieProduit.body) {
            return of(categorieProduit.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CategorieProduit());
  }
}

export const categorieProduitRoute: Routes = [
  {
    path: '',
    component: CategorieProduitComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'magasinFactureApp.categorieProduit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CategorieProduitDetailComponent,
    resolve: {
      categorieProduit: CategorieProduitResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'magasinFactureApp.categorieProduit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CategorieProduitUpdateComponent,
    resolve: {
      categorieProduit: CategorieProduitResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'magasinFactureApp.categorieProduit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CategorieProduitUpdateComponent,
    resolve: {
      categorieProduit: CategorieProduitResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'magasinFactureApp.categorieProduit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
