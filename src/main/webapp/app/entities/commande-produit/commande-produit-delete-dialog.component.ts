import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommandeProduit } from 'app/shared/model/commande-produit.model';
import { CommandeProduitService } from './commande-produit.service';

@Component({
  templateUrl: './commande-produit-delete-dialog.component.html',
})
export class CommandeProduitDeleteDialogComponent {
  commandeProduit?: ICommandeProduit;

  constructor(
    protected commandeProduitService: CommandeProduitService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.commandeProduitService.delete(id).subscribe(() => {
      this.eventManager.broadcast('commandeProduitListModification');
      this.activeModal.close();
    });
  }
}
