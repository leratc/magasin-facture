import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IClient, Client } from 'app/shared/model/client.model';
import { ClientService } from './client.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-client-update',
  templateUrl: './client-update.component.html',
})
export class ClientUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    prenom: [null, [Validators.required]],
    nom: [null, [Validators.required]],
    civilite: [null, [Validators.required]],
    email: [null, [Validators.required, Validators.pattern('^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$')]],
    telephone: [null, [Validators.required]],
    addresseLigne1: [null, [Validators.required]],
    addresseLigne2: [],
    ville: [null, [Validators.required]],
    pays: [null, [Validators.required]],
    user: [null, Validators.required],
  });

  constructor(
    protected clientService: ClientService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ client }) => {
      this.updateForm(client);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(client: IClient): void {
    this.editForm.patchValue({
      id: client.id,
      prenom: client.prenom,
      nom: client.nom,
      civilite: client.civilite,
      email: client.email,
      telephone: client.telephone,
      addresseLigne1: client.addresseLigne1,
      addresseLigne2: client.addresseLigne2,
      ville: client.ville,
      pays: client.pays,
      user: client.user,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const client = this.createFromForm();
    if (client.id !== undefined) {
      this.subscribeToSaveResponse(this.clientService.update(client));
    } else {
      this.subscribeToSaveResponse(this.clientService.create(client));
    }
  }

  private createFromForm(): IClient {
    return {
      ...new Client(),
      id: this.editForm.get(['id'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      civilite: this.editForm.get(['civilite'])!.value,
      email: this.editForm.get(['email'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      addresseLigne1: this.editForm.get(['addresseLigne1'])!.value,
      addresseLigne2: this.editForm.get(['addresseLigne2'])!.value,
      ville: this.editForm.get(['ville'])!.value,
      pays: this.editForm.get(['pays'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClient>>): void {
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

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
