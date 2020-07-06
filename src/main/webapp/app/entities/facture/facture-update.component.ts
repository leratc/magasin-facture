import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IFacture, Facture } from 'app/shared/model/facture.model';
import { FactureService } from './facture.service';

@Component({
  selector: 'jhi-facture-update',
  templateUrl: './facture-update.component.html',
})
export class FactureUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    date: [null, [Validators.required]],
    details: [],
    statut: [null, [Validators.required]],
    methodePaiement: [null, [Validators.required]],
    datePaiement: [null, [Validators.required]],
    montantPaiement: [null, [Validators.required]],
  });

  constructor(protected factureService: FactureService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ facture }) => {
      if (!facture.id) {
        const today = moment().startOf('day');
        facture.date = today;
        facture.datePaiement = today;
      }

      this.updateForm(facture);
    });
  }

  updateForm(facture: IFacture): void {
    this.editForm.patchValue({
      id: facture.id,
      code: facture.code,
      date: facture.date ? facture.date.format(DATE_TIME_FORMAT) : null,
      details: facture.details,
      statut: facture.statut,
      methodePaiement: facture.methodePaiement,
      datePaiement: facture.datePaiement ? facture.datePaiement.format(DATE_TIME_FORMAT) : null,
      montantPaiement: facture.montantPaiement,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const facture = this.createFromForm();
    if (facture.id !== undefined) {
      this.subscribeToSaveResponse(this.factureService.update(facture));
    } else {
      this.subscribeToSaveResponse(this.factureService.create(facture));
    }
  }

  private createFromForm(): IFacture {
    return {
      ...new Facture(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      date: this.editForm.get(['date'])!.value ? moment(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      details: this.editForm.get(['details'])!.value,
      statut: this.editForm.get(['statut'])!.value,
      methodePaiement: this.editForm.get(['methodePaiement'])!.value,
      datePaiement: this.editForm.get(['datePaiement'])!.value
        ? moment(this.editForm.get(['datePaiement'])!.value, DATE_TIME_FORMAT)
        : undefined,
      montantPaiement: this.editForm.get(['montantPaiement'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFacture>>): void {
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
