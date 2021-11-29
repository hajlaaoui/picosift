import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDossier, getDossierIdentifier } from '../dossier.model';

export type EntityResponseType = HttpResponse<IDossier>;
export type EntityArrayResponseType = HttpResponse<IDossier[]>;

@Injectable({ providedIn: 'root' })
export class DossierService {

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/dossiers');
  protected resde = this.applicationConfigService.getEndpointFor('api/update-dossDenied');
  protected resacc = this.applicationConfigService.getEndpointFor('api/update-dossAccepter');





  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  updatetodeny(id: number):Observable<any> {

    return this.http.put<IDossier>(`${this.resde}/${id}`, { observe: 'response' });
    //return this.http.get(this.urldeny/, { headers: headers })
  }
  updatetoaccept(id: number):Observable<any> {

    return this.http.put<IDossier>(`${this.resacc}/${id}`, { observe: 'response' });
    //return this.http.get(this.urldeny/, { headers: headers })
  }

  create(dossier: IDossier): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dossier);
    return this.http
      .post<IDossier>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dossier: IDossier): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dossier);
    return this.http
      .put<IDossier>(`${this.resourceUrl}/${getDossierIdentifier(dossier) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }





  partialUpdate(dossier: IDossier): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dossier);
    return this.http
      .patch<IDossier>(`${this.resourceUrl}/${getDossierIdentifier(dossier) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDossier>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDossier[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDossierToCollectionIfMissing(dossierCollection: IDossier[], ...dossiersToCheck: (IDossier | null | undefined)[]): IDossier[] {
    const dossiers: IDossier[] = dossiersToCheck.filter(isPresent);
    if (dossiers.length > 0) {
      const dossierCollectionIdentifiers = dossierCollection.map(dossierItem => getDossierIdentifier(dossierItem)!);
      const dossiersToAdd = dossiers.filter(dossierItem => {
        const dossierIdentifier = getDossierIdentifier(dossierItem);
        if (dossierIdentifier == null || dossierCollectionIdentifiers.includes(dossierIdentifier)) {
          return false;
        }
        dossierCollectionIdentifiers.push(dossierIdentifier);
        return true;
      });
      return [...dossiersToAdd, ...dossierCollection];
    }
    return dossierCollection;
  }

  protected convertDateFromClient(dossier: IDossier): IDossier {
    return Object.assign({}, dossier, {
      datedesicion: dossier.datedesicion?.isValid() ? dossier.datedesicion.format(DATE_FORMAT) : undefined,
      dateValidation: dossier.dateValidation?.isValid() ? dossier.dateValidation.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.datedesicion = res.body.datedesicion ? dayjs(res.body.datedesicion) : undefined;
      res.body.dateValidation = res.body.dateValidation ? dayjs(res.body.dateValidation) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((dossier: IDossier) => {
        dossier.datedesicion = dossier.datedesicion ? dayjs(dossier.datedesicion) : undefined;
        dossier.dateValidation = dossier.dateValidation ? dayjs(dossier.dateValidation) : undefined;
      });
    }
    return res;
  }






}
