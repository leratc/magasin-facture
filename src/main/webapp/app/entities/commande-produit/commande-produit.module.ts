import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MagasinFactureSharedModule } from 'app/shared/shared.module';
import { CommandeProduitComponent } from './commande-produit.component';
import { CommandeProduitDetailComponent } from './commande-produit-detail.component';
import { CommandeProduitUpdateComponent } from './commande-produit-update.component';
import { CommandeProduitDeleteDialogComponent } from './commande-produit-delete-dialog.component';
import { commandeProduitRoute } from './commande-produit.route';

@NgModule({
  imports: [MagasinFactureSharedModule, RouterModule.forChild(commandeProduitRoute)],
  declarations: [
    CommandeProduitComponent,
    CommandeProduitDetailComponent,
    CommandeProduitUpdateComponent,
    CommandeProduitDeleteDialogComponent,
  ],
  entryComponents: [CommandeProduitDeleteDialogComponent],
})
export class MagasinFactureCommandeProduitModule {}
