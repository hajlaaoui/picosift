import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDemande, Demande } from '../demande.model';
import { DemandeService } from '../service/demande.service';
import { IDossier } from 'app/entities/dossier/dossier.model';
import { DossierService } from 'app/entities/dossier/service/dossier.service';

@Component({
  selector: 'jhi-demande-update',
  templateUrl: './demande-update.component.html',
})
export class DemandeUpdateComponent implements OnInit {
  isSaving = false;

  dossiersSharedCollection: IDossier[] = [];

  editForm = this.fb.group({
    id: [],
    refSiteWeb: [null, [Validators.required]],
    datedesicion: [],
    importateur: [],
    refBF: [null, []],
    marque: [],
    modele: [],
    numeroSerie: [null, []],
    imei1: [null, []],
    imei2: [null, []],
    imei3: [null, []],
    statut: [],
    dateCreation: [],
    dateDepotSiteWeb: [],
    dateValidation: [],
    dateExport: [],
    commentaire: [null, [Validators.required]],
    dossier: [],
  });

  constructor(
    protected demandeService: DemandeService,
    protected dossierService: DossierService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demande }) => {
      this.updateForm(demande);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const demande = this.createFromForm();
    if (demande.id !== undefined) {
      this.subscribeToSaveResponse(this.demandeService.update(demande));
    } else {
      this.subscribeToSaveResponse(this.demandeService.create(demande));
    }
  }

  trackDossierById(index: number, item: IDossier): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDemande>>): void {
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

  protected updateForm(demande: IDemande): void {
    this.editForm.patchValue({
      id: demande.id,
      refSiteWeb: demande.refSiteWeb,
      datedesicion: demande.datedesicion,
      importateur: demande.importateur,
      refBF: demande.refBF,
      marque: demande.marque,
      modele: demande.modele,
      numeroSerie: demande.numeroSerie,
      imei1: demande.imei1,
      imei2: demande.imei2,
      imei3: demande.imei3,
      statut: demande.statut,
      dateCreation: demande.dateCreation,
      dateDepotSiteWeb: demande.dateDepotSiteWeb,
      dateValidation: demande.dateValidation,
      dateExport: demande.dateExport,
      commentaire: demande.commentaire,
      dossier: demande.dossier,
    });

    this.dossiersSharedCollection = this.dossierService.addDossierToCollectionIfMissing(this.dossiersSharedCollection, demande.dossier);
  }

  protected loadRelationshipsOptions(): void {
    this.dossierService
      .query()
      .pipe(map((res: HttpResponse<IDossier[]>) => res.body ?? []))
      .pipe(
        map((dossiers: IDossier[]) => this.dossierService.addDossierToCollectionIfMissing(dossiers, this.editForm.get('dossier')!.value))
      )
      .subscribe((dossiers: IDossier[]) => (this.dossiersSharedCollection = dossiers));
  }

  protected createFromForm(): IDemande {
    return {
      ...new Demande(),
      id: this.editForm.get(['id'])!.value,
      refSiteWeb: this.editForm.get(['refSiteWeb'])!.value,
      datedesicion: this.editForm.get(['datedesicion'])!.value,
      importateur: this.editForm.get(['importateur'])!.value,
      refBF: this.editForm.get(['refBF'])!.value,
      marque: this.editForm.get(['marque'])!.value,
      modele: this.editForm.get(['modele'])!.value,
      numeroSerie: this.editForm.get(['numeroSerie'])!.value,
      imei1: this.editForm.get(['imei1'])!.value,
      imei2: this.editForm.get(['imei2'])!.value,
      imei3: this.editForm.get(['imei3'])!.value,
      statut: this.editForm.get(['statut'])!.value,
      dateCreation: this.editForm.get(['dateCreation'])!.value,
      dateDepotSiteWeb: this.editForm.get(['dateDepotSiteWeb'])!.value,
      dateValidation: this.editForm.get(['dateValidation'])!.value,
      dateExport: this.editForm.get(['dateExport'])!.value,
      commentaire: this.editForm.get(['commentaire'])!.value,
      dossier: this.editForm.get(['dossier'])!.value,
    };
  }
}
