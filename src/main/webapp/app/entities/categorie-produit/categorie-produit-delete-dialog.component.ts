import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICategorieProduit } from 'app/shared/model/categorie-produit.model';
import { CategorieProduitService } from './categorie-produit.service';

@Component({
  templateUrl: './categorie-produit-delete-dialog.component.html',
})
export class CategorieProduitDeleteDialogComponent {
  categorieProduit?: ICategorieProduit;

  constructor(
    protected categorieProduitService: CategorieProduitService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.categorieProduitService.delete(id).subscribe(() => {
      this.eventManager.broadcast('categorieProduitListModification');
      this.activeModal.close();
    });
  }
}
