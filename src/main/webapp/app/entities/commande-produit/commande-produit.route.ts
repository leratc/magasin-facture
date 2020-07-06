import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICommandeProduit, CommandeProduit } from 'app/shared/model/commande-produit.model';
import { CommandeProduitService } from './commande-produit.service';
import { CommandeProduitComponent } from './commande-produit.component';
import { CommandeProduitDetailComponent } from './commande-produit-detail.component';
import { CommandeProduitUpdateComponent } from './commande-produit-update.component';

@Injectable({ providedIn: 'root' })
export class CommandeProduitResolve implements Resolve<ICommandeProduit> {
  constructor(private service: CommandeProduitService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICommandeProduit> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((commandeProduit: HttpResponse<CommandeProduit>) => {
          if (commandeProduit.body) {
            return of(commandeProduit.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CommandeProduit());
  }
}

export const commandeProduitRoute: Routes = [
  {
    path: '',
    component: CommandeProduitComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'magasinFactureApp.commandeProduit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CommandeProduitDetailComponent,
    resolve: {
      commandeProduit: CommandeProduitResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'magasinFactureApp.commandeProduit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CommandeProduitUpdateComponent,
    resolve: {
      commandeProduit: CommandeProduitResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'magasinFactureApp.commandeProduit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CommandeProduitUpdateComponent,
    resolve: {
      commandeProduit: CommandeProduitResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'magasinFactureApp.commandeProduit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
