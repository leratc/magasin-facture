import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILigneCommande } from 'app/shared/model/ligne-commande.model';
import { LigneCommandeService } from './ligne-commande.service';

@Component({
  templateUrl: './ligne-commande-delete-dialog.component.html',
})
export class LigneCommandeDeleteDialogComponent {
  ligneCommande?: ILigneCommande;

  constructor(
    protected ligneCommandeService: LigneCommandeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ligneCommandeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('ligneCommandeListModification');
      this.activeModal.close();
    });
  }
}
