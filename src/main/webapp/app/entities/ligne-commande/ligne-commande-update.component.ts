import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILigneCommande, LigneCommande } from 'app/shared/model/ligne-commande.model';
import { LigneCommandeService } from './ligne-commande.service';
import { IProduit } from 'app/shared/model/produit.model';
import { ProduitService } from 'app/entities/produit/produit.service';
import { ICommandeProduit } from 'app/shared/model/commande-produit.model';
import { CommandeProduitService } from 'app/entities/commande-produit/commande-produit.service';

type SelectableEntity = IProduit | ICommandeProduit;

@Component({
  selector: 'jhi-ligne-commande-update',
  templateUrl: './ligne-commande-update.component.html',
})
export class LigneCommandeUpdateComponent implements OnInit {
  isSaving = false;
  produits: IProduit[] = [];
  commandeproduits: ICommandeProduit[] = [];

  editForm = this.fb.group({
    id: [],
    quantite: [null, [Validators.required, Validators.min(0)]],
    prixTotalHT: [null, [Validators.required, Validators.min(0)]],
    statut: [null, [Validators.required]],
    produit: [null, Validators.required],
    commande: [null, Validators.required],
  });

  constructor(
    protected ligneCommandeService: LigneCommandeService,
    protected produitService: ProduitService,
    protected commandeProduitService: CommandeProduitService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ligneCommande }) => {
      this.updateForm(ligneCommande);

      this.produitService.query().subscribe((res: HttpResponse<IProduit[]>) => (this.produits = res.body || []));

      this.commandeProduitService.query().subscribe((res: HttpResponse<ICommandeProduit[]>) => (this.commandeproduits = res.body || []));
    });
  }

  updateForm(ligneCommande: ILigneCommande): void {
    this.editForm.patchValue({
      id: ligneCommande.id,
      quantite: ligneCommande.quantite,
      prixTotalHT: ligneCommande.prixTotalHT,
      statut: ligneCommande.statut,
      produit: ligneCommande.produit,
      commande: ligneCommande.commande,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ligneCommande = this.createFromForm();
    if (ligneCommande.id !== undefined) {
      this.subscribeToSaveResponse(this.ligneCommandeService.update(ligneCommande));
    } else {
      this.subscribeToSaveResponse(this.ligneCommandeService.create(ligneCommande));
    }
  }

  private createFromForm(): ILigneCommande {
    return {
      ...new LigneCommande(),
      id: this.editForm.get(['id'])!.value,
      quantite: this.editForm.get(['quantite'])!.value,
      prixTotalHT: this.editForm.get(['prixTotalHT'])!.value,
      statut: this.editForm.get(['statut'])!.value,
      produit: this.editForm.get(['produit'])!.value,
      commande: this.editForm.get(['commande'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILigneCommande>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
