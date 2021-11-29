import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IDossier, Dossier } from '../dossier.model';
import { DossierService } from '../service/dossier.service';

@Component({
  selector: 'jhi-dossier-update',
  templateUrl: './dossier-update.component.html',
})
export class DossierUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    refBF: [null, [Validators.required]],
    datedesicion: [],
    cin: [null, [Validators.required]],
    passeport: [null, []],
    importateur: [],
    marque: [],
    statut: [],
    numAvisArrive: [],
    dateValidation: [],
    commentaire: [null, [Validators.required]],
  });

  constructor(protected dossierService: DossierService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dossier }) => {
      this.updateForm(dossier);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dossier = this.createFromForm();
    if (dossier.id !== undefined) {
      this.subscribeToSaveResponse(this.dossierService.update(dossier));
    } else {
      this.subscribeToSaveResponse(this.dossierService.create(dossier));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDossier>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(dossier: IDossier): void {
    this.editForm.patchValue({
      id: dossier.id,
      refBF: dossier.refBF,
      datedesicion: dossier.datedesicion,
      cin: dossier.cin,
      passeport: dossier.passeport,
      importateur: dossier.importateur,
      marque: dossier.marque,
      statut: dossier.statut,
      numAvisArrive: dossier.numAvisArrive,
      dateValidation: dossier.dateValidation,
      commentaire: dossier.commentaire,
    });
  }

  protected createFromForm(): IDossier {
    return {
      ...new Dossier(),
      id: this.editForm.get(['id'])!.value,
      refBF: this.editForm.get(['refBF'])!.value,
      datedesicion: this.editForm.get(['datedesicion'])!.value,
      cin: this.editForm.get(['cin'])!.value,
      passeport: this.editForm.get(['passeport'])!.value,
      importateur: this.editForm.get(['importateur'])!.value,
      marque: this.editForm.get(['marque'])!.value,
      statut: this.editForm.get(['statut'])!.value,
      numAvisArrive: this.editForm.get(['numAvisArrive'])!.value,
      dateValidation: this.editForm.get(['dateValidation'])!.value,
      commentaire: this.editForm.get(['commentaire'])!.value,
    };
  }
}
