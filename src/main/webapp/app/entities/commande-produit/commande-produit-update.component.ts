import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICommandeProduit, CommandeProduit } from 'app/shared/model/commande-produit.model';
import { CommandeProduitService } from './commande-produit.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client/client.service';

@Component({
  selector: 'jhi-commande-produit-update',
  templateUrl: './commande-produit-update.component.html',
})
export class CommandeProduitUpdateComponent implements OnInit {
  isSaving = false;
  clients: IClient[] = [];

  editForm = this.fb.group({
    id: [],
    dateCommande: [null, [Validators.required]],
    statut: [null, [Validators.required]],
    code: [null, [Validators.required]],
    factureId: [],
    client: [null, Validators.required],
  });

  constructor(
    protected commandeProduitService: CommandeProduitService,
    protected clientService: ClientService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commandeProduit }) => {
      if (!commandeProduit.id) {
        const today = moment().startOf('day');
        commandeProduit.dateCommande = today;
      }

      this.updateForm(commandeProduit);

      this.clientService.query().subscribe((res: HttpResponse<IClient[]>) => (this.clients = res.body || []));
    });
  }

  updateForm(commandeProduit: ICommandeProduit): void {
    this.editForm.patchValue({
      id: commandeProduit.id,
      dateCommande: commandeProduit.dateCommande ? commandeProduit.dateCommande.format(DATE_TIME_FORMAT) : null,
      statut: commandeProduit.statut,
      code: commandeProduit.code,
      factureId: commandeProduit.factureId,
      client: commandeProduit.client,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commandeProduit = this.createFromForm();
    if (commandeProduit.id !== undefined) {
      this.subscribeToSaveResponse(this.commandeProduitService.update(commandeProduit));
    } else {
      this.subscribeToSaveResponse(this.commandeProduitService.create(commandeProduit));
    }
  }

  private createFromForm(): ICommandeProduit {
    return {
      ...new CommandeProduit(),
      id: this.editForm.get(['id'])!.value,
      dateCommande: this.editForm.get(['dateCommande'])!.value
        ? moment(this.editForm.get(['dateCommande'])!.value, DATE_TIME_FORMAT)
        : undefined,
      statut: this.editForm.get(['statut'])!.value,
      code: this.editForm.get(['code'])!.value,
      factureId: this.editForm.get(['factureId'])!.value,
      client: this.editForm.get(['client'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommandeProduit>>): void {
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

  trackById(index: number, item: IClient): any {
    return item.id;
  }
}
