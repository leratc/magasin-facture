import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MagasinFactureSharedModule } from 'app/shared/shared.module';
import { LigneCommandeComponent } from './ligne-commande.component';
import { LigneCommandeDetailComponent } from './ligne-commande-detail.component';
import { LigneCommandeUpdateComponent } from './ligne-commande-update.component';
import { LigneCommandeDeleteDialogComponent } from './ligne-commande-delete-dialog.component';
import { ligneCommandeRoute } from './ligne-commande.route';

@NgModule({
  imports: [MagasinFactureSharedModule, RouterModule.forChild(ligneCommandeRoute)],
  declarations: [LigneCommandeComponent, LigneCommandeDetailComponent, LigneCommandeUpdateComponent, LigneCommandeDeleteDialogComponent],
  entryComponents: [LigneCommandeDeleteDialogComponent],
})
export class MagasinFactureLigneCommandeModule {}
