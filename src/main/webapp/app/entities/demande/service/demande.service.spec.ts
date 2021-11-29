import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { Statut } from 'app/entities/enumerations/statut.model';
import { IDemande, Demande } from '../demande.model';

import { DemandeService } from './demande.service';

describe('Service Tests', () => {
  describe('Demande Service', () => {
    let service: DemandeService;
    let httpMock: HttpTestingController;
    let elemDefault: IDemande;
    let expectedResult: IDemande | IDemande[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DemandeService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        refSiteWeb: 'AAAAAAA',
        datedesicion: currentDate,
        importateur: 'AAAAAAA',
        refBF: 'AAAAAAA',
        marque: 'AAAAAAA',
        modele: 'AAAAAAA',
        numeroSerie: 'AAAAAAA',
        imei1: 'AAAAAAA',
        imei2: 'AAAAAAA',
        imei3: 'AAAAAAA',
        statut: Statut.EN_COURS,
        dateCreation: currentDate,
        dateDepotSiteWeb: currentDate,
        dateValidation: currentDate,
        dateExport: currentDate,
        commentaire: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            datedesicion: currentDate.format(DATE_FORMAT),
            dateCreation: currentDate.format(DATE_FORMAT),
            dateDepotSiteWeb: currentDate.format(DATE_FORMAT),
            dateValidation: currentDate.format(DATE_FORMAT),
            dateExport: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Demande', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            datedesicion: currentDate.format(DATE_FORMAT),
            dateCreation: currentDate.format(DATE_FORMAT),
            dateDepotSiteWeb: currentDate.format(DATE_FORMAT),
            dateValidation: currentDate.format(DATE_FORMAT),
            dateExport: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datedesicion: currentDate,
            dateCreation: currentDate,
            dateDepotSiteWeb: currentDate,
            dateValidation: currentDate,
            dateExport: currentDate,
          },
          returnedFromService
        );

        service.create(new Demande()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Demande', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            refSiteWeb: 'BBBBBB',
            datedesicion: currentDate.format(DATE_FORMAT),
            importateur: 'BBBBBB',
            refBF: 'BBBBBB',
            marque: 'BBBBBB',
            modele: 'BBBBBB',
            numeroSerie: 'BBBBBB',
            imei1: 'BBBBBB',
            imei2: 'BBBBBB',
            imei3: 'BBBBBB',
            statut: 'BBBBBB',
            dateCreation: currentDate.format(DATE_FORMAT),
            dateDepotSiteWeb: currentDate.format(DATE_FORMAT),
            dateValidation: currentDate.format(DATE_FORMAT),
            dateExport: currentDate.format(DATE_FORMAT),
            commentaire: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datedesicion: currentDate,
            dateCreation: currentDate,
            dateDepotSiteWeb: currentDate,
            dateValidation: currentDate,
            dateExport: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Demande', () => {
        const patchObject = Object.assign(
          {
            refSiteWeb: 'BBBBBB',
            datedesicion: currentDate.format(DATE_FORMAT),
            importateur: 'BBBBBB',
            marque: 'BBBBBB',
            modele: 'BBBBBB',
            numeroSerie: 'BBBBBB',
            imei1: 'BBBBBB',
            dateValidation: currentDate.format(DATE_FORMAT),
            dateExport: currentDate.format(DATE_FORMAT),
          },
          new Demande()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            datedesicion: currentDate,
            dateCreation: currentDate,
            dateDepotSiteWeb: currentDate,
            dateValidation: currentDate,
            dateExport: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Demande', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            refSiteWeb: 'BBBBBB',
            datedesicion: currentDate.format(DATE_FORMAT),
            importateur: 'BBBBBB',
            refBF: 'BBBBBB',
            marque: 'BBBBBB',
            modele: 'BBBBBB',
            numeroSerie: 'BBBBBB',
            imei1: 'BBBBBB',
            imei2: 'BBBBBB',
            imei3: 'BBBBBB',
            statut: 'BBBBBB',
            dateCreation: currentDate.format(DATE_FORMAT),
            dateDepotSiteWeb: currentDate.format(DATE_FORMAT),
            dateValidation: currentDate.format(DATE_FORMAT),
            dateExport: currentDate.format(DATE_FORMAT),
            commentaire: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datedesicion: currentDate,
            dateCreation: currentDate,
            dateDepotSiteWeb: currentDate,
            dateValidation: currentDate,
            dateExport: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Demande', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDemandeToCollectionIfMissing', () => {
        it('should add a Demande to an empty array', () => {
          const demande: IDemande = { id: 123 };
          expectedResult = service.addDemandeToCollectionIfMissing([], demande);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(demande);
        });

        it('should not add a Demande to an array that contains it', () => {
          const demande: IDemande = { id: 123 };
          const demandeCollection: IDemande[] = [
            {
              ...demande,
            },
            { id: 456 },
          ];
          expectedResult = service.addDemandeToCollectionIfMissing(demandeCollection, demande);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Demande to an array that doesn't contain it", () => {
          const demande: IDemande = { id: 123 };
          const demandeCollection: IDemande[] = [{ id: 456 }];
          expectedResult = service.addDemandeToCollectionIfMissing(demandeCollection, demande);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(demande);
        });

        it('should add only unique Demande to an array', () => {
          const demandeArray: IDemande[] = [{ id: 123 }, { id: 456 }, { id: 62070 }];
          const demandeCollection: IDemande[] = [{ id: 123 }];
          expectedResult = service.addDemandeToCollectionIfMissing(demandeCollection, ...demandeArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const demande: IDemande = { id: 123 };
          const demande2: IDemande = { id: 456 };
          expectedResult = service.addDemandeToCollectionIfMissing([], demande, demande2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(demande);
          expect(expectedResult).toContain(demande2);
        });

        it('should accept null and undefined values', () => {
          const demande: IDemande = { id: 123 };
          expectedResult = service.addDemandeToCollectionIfMissing([], null, demande, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(demande);
        });

        it('should return initial array if no Demande is added', () => {
          const demandeCollection: IDemande[] = [{ id: 123 }];
          expectedResult = service.addDemandeToCollectionIfMissing(demandeCollection, undefined, null);
          expect(expectedResult).toEqual(demandeCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
