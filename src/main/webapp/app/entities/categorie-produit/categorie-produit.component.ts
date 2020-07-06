import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICategorieProduit } from 'app/shared/model/categorie-produit.model';
import { CategorieProduitService } from './categorie-produit.service';
import { CategorieProduitDeleteDialogComponent } from './categorie-produit-delete-dialog.component';

@Component({
  selector: 'jhi-categorie-produit',
  templateUrl: './categorie-produit.component.html',
})
export class CategorieProduitComponent implements OnInit, OnDestroy {
  categorieProduits?: ICategorieProduit[];
  eventSubscriber?: Subscription;

  constructor(
    protected categorieProduitService: CategorieProduitService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.categorieProduitService.query().subscribe((res: HttpResponse<ICategorieProduit[]>) => (this.categorieProduits = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCategorieProduits();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICategorieProduit): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCategorieProduits(): void {
    this.eventSubscriber = this.eventManager.subscribe('categorieProduitListModification', () => this.loadAll());
  }

  delete(categorieProduit: ICategorieProduit): void {
    const modalRef = this.modalService.open(CategorieProduitDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.categorieProduit = categorieProduit;
  }
}
