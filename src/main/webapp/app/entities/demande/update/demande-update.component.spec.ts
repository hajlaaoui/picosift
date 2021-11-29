jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DemandeService } from '../service/demande.service';
import { IDemande, Demande } from '../demande.model';
import { IDossier } from 'app/entities/dossier/dossier.model';
import { DossierService } from 'app/entities/dossier/service/dossier.service';

import { DemandeUpdateComponent } from './demande-update.component';

describe('Component Tests', () => {
  describe('Demande Management Update Component', () => {
    let comp: DemandeUpdateComponent;
    let fixture: ComponentFixture<DemandeUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let demandeService: DemandeService;
    let dossierService: DossierService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DemandeUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DemandeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DemandeUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      demandeService = TestBed.inject(DemandeService);
      dossierService = TestBed.inject(DossierService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Dossier query and add missing value', () => {
        const demande: IDemande = { id: 456 };
        const dossier: IDossier = { id: 36459 };
        demande.dossier = dossier;

        const dossierCollection: IDossier[] = [{ id: 49196 }];
        jest.spyOn(dossierService, 'query').mockReturnValue(of(new HttpResponse({ body: dossierCollection })));
        const additionalDossiers = [dossier];
        const expectedCollection: IDossier[] = [...additionalDossiers, ...dossierCollection];
        jest.spyOn(dossierService, 'addDossierToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ demande });
        comp.ngOnInit();

        expect(dossierService.query).toHaveBeenCalled();
        expect(dossierService.addDossierToCollectionIfMissing).toHaveBeenCalledWith(dossierCollection, ...additionalDossiers);
        expect(comp.dossiersSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const demande: IDemande = { id: 456 };
        const dossier: IDossier = { id: 15096 };
        demande.dossier = dossier;

        activatedRoute.data = of({ demande });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(demande));
        expect(comp.dossiersSharedCollection).toContain(dossier);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Demande>>();
        const demande = { id: 123 };
        jest.spyOn(demandeService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ demande });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: demande }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(demandeService.update).toHaveBeenCalledWith(demande);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Demande>>();
        const demande = new Demande();
        jest.spyOn(demandeService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ demande });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: demande }));
        saveSubject.complete();

        // THEN
        expect(demandeService.create).toHaveBeenCalledWith(demande);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Demande>>();
        const demande = { id: 123 };
        jest.spyOn(demandeService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ demande });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(demandeService.update).toHaveBeenCalledWith(demande);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackDossierById', () => {
        it('Should return tracked Dossier primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackDossierById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
