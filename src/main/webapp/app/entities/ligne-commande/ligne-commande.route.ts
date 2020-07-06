import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILigneCommande, LigneCommande } from 'app/shared/model/ligne-commande.model';
import { LigneCommandeService } from './ligne-commande.service';
import { LigneCommandeComponent } from './ligne-commande.component';
import { LigneCommandeDetailComponent } from './ligne-commande-detail.component';
import { LigneCommandeUpdateComponent } from './ligne-commande-update.component';

@Injectable({ providedIn: 'root' })
export class LigneCommandeResolve implements Resolve<ILigneCommande> {
  constructor(private service: LigneCommandeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILigneCommande> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((ligneCommande: HttpResponse<LigneCommande>) => {
          if (ligneCommande.body) {
            return of(ligneCommande.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LigneCommande());
  }
}

export const ligneCommandeRoute: Routes = [
  {
    path: '',
    component: LigneCommandeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'magasinFactureApp.ligneCommande.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LigneCommandeDetailComponent,
    resolve: {
      ligneCommande: LigneCommandeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'magasinFactureApp.ligneCommande.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LigneCommandeUpdateComponent,
    resolve: {
      ligneCommande: LigneCommandeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'magasinFactureApp.ligneCommande.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LigneCommandeUpdateComponent,
    resolve: {
      ligneCommande: LigneCommandeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'magasinFactureApp.ligneCommande.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
