import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'produit',
        loadChildren: () => import('./produit/produit.module').then(m => m.MagasinFactureProduitModule),
      },
      {
        path: 'categorie-produit',
        loadChildren: () => import('./categorie-produit/categorie-produit.module').then(m => m.MagasinFactureCategorieProduitModule),
      },
      {
        path: 'client',
        loadChildren: () => import('./client/client.module').then(m => m.MagasinFactureClientModule),
      },
      {
        path: 'commande-produit',
        loadChildren: () => import('./commande-produit/commande-produit.module').then(m => m.MagasinFactureCommandeProduitModule),
      },
      {
        path: 'ligne-commande',
        loadChildren: () => import('./ligne-commande/ligne-commande.module').then(m => m.MagasinFactureLigneCommandeModule),
      },
      {
        path: 'facture',
        loadChildren: () => import('./facture/facture.module').then(m => m.MagasinFactureFactureModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class MagasinFactureEntityModule {}
