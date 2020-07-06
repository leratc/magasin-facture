import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICategorieProduit, CategorieProduit } from 'app/shared/model/categorie-produit.model';
import { CategorieProduitService } from './categorie-produit.service';

@Component({
  selector: 'jhi-categorie-produit-update',
  templateUrl: './categorie-produit-update.component.html',
})
export class CategorieProduitUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    description: [],
    tauxTaxe: [],
    importe: [],
  });

  constructor(
    protected categorieProduitService: CategorieProduitService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categorieProduit }) => {
      this.updateForm(categorieProduit);
    });
  }

  updateForm(categorieProduit: ICategorieProduit): void {
    this.editForm.patchValue({
      id: categorieProduit.id,
      nom: categorieProduit.nom,
      description: categorieProduit.description,
      tauxTaxe: categorieProduit.tauxTaxe,
      importe: categorieProduit.importe,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const categorieProduit = this.createFromForm();
    if (categorieProduit.id !== undefined) {
      this.subscribeToSaveResponse(this.categorieProduitService.update(categorieProduit));
    } else {
      this.subscribeToSaveResponse(this.categorieProduitService.create(categorieProduit));
    }
  }

  private createFromForm(): ICategorieProduit {
    return {
      ...new CategorieProduit(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      description: this.editForm.get(['description'])!.value,
      tauxTaxe: this.editForm.get(['tauxTaxe'])!.value,
      importe: this.editForm.get(['importe'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategorieProduit>>): void {
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
}
