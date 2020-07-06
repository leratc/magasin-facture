import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MagasinFactureSharedModule } from 'app/shared/shared.module';
import { CategorieProduitComponent } from './categorie-produit.component';
import { CategorieProduitDetailComponent } from './categorie-produit-detail.component';
import { CategorieProduitUpdateComponent } from './categorie-produit-update.component';
import { CategorieProduitDeleteDialogComponent } from './categorie-produit-delete-dialog.component';
import { categorieProduitRoute } from './categorie-produit.route';

@NgModule({
  imports: [MagasinFactureSharedModule, RouterModule.forChild(categorieProduitRoute)],
  declarations: [
    CategorieProduitComponent,
    CategorieProduitDetailComponent,
    CategorieProduitUpdateComponent,
    CategorieProduitDeleteDialogComponent,
  ],
  entryComponents: [CategorieProduitDeleteDialogComponent],
})
export class MagasinFactureCategorieProduitModule {}
