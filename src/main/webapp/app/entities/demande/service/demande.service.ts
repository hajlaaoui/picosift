import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDemande, getDemandeIdentifier } from '../demande.model';

export type EntityResponseType = HttpResponse<IDemande>;
export type EntityArrayResponseType = HttpResponse<IDemande[]>;
const apiKey = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTYzMTY3MDgzMH0.ROULsdunQdJiDoGeBMKZnxhn_unpMlnfqlCH2N_dEgdjwiDBTdHVmSpnKIAUDqUqfWze8yqs7utFGh2CBsPLDw';

@Injectable({ providedIn: 'root' })
export class DemandeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/demandes');
  protected resde = this.applicationConfigService.getEndpointFor('api/update-demDenied');
  protected resacc = this.applicationConfigService.getEndpointFor('api/update-demAccepter');
  protected resdate = this.applicationConfigService.getEndpointFor('api/calculateinterval');
  protected nbrefuser = this.applicationConfigService.getEndpointFor('api/countrefuser');


  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}


  nbaccepter():Observable<any> {

    return this.http.get<IDemande>(`${this.nbrefuser}/`, { observe: 'response' });
    //return this.http.get(this.urldeny/, { headers: headers })
  }


  calculintervale(id: number):Observable<any> {

    return this.http.get<IDemande>(`${this.resdate}/${id}`, { observe: 'response' })
    .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));

  }
  updatetodeny(id: number):Observable<any> {

    return this.http.put<IDemande>(`${this.resde}/${id}`, { observe: 'response' });
    //return this.http.get(this.urldeny/, { headers: headers })
  }
  updatetoaccept(id: number):Observable<any> {

    return this.http.put<IDemande>(`${this.resacc}/${id}`, { observe: 'response' });
    //return this.http.get(this.urldeny/, { headers: headers })
  }

  create(demande: IDemande): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demande);
    return this.http.post<IDemande>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(demande: IDemande): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demande);
    return this.http
      .put<IDemande>(`${this.resourceUrl}/${getDemandeIdentifier(demande) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(demande: IDemande): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demande);
    return this.http
      .patch<IDemande>(`${this.resourceUrl}/${getDemandeIdentifier(demande) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDemande>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDemande[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDemandeToCollectionIfMissing(demandeCollection: IDemande[], ...demandesToCheck: (IDemande | null | undefined)[]): IDemande[] {
    const demandes: IDemande[] = demandesToCheck.filter(isPresent);
    if (demandes.length > 0) {
      const demandeCollectionIdentifiers = demandeCollection.map(demandeItem => getDemandeIdentifier(demandeItem)!);
      const demandesToAdd = demandes.filter(demandeItem => {
        const demandeIdentifier = getDemandeIdentifier(demandeItem);
        if (demandeIdentifier == null || demandeCollectionIdentifiers.includes(demandeIdentifier)) {
          return false;
        }
        demandeCollectionIdentifiers.push(demandeIdentifier);
        return true;
      });
      return [...demandesToAdd, ...demandeCollection];
    }
    return demandeCollection;
  }

  protected convertDateFromClient(demande: IDemande): IDemande {
    return Object.assign({}, demande, {
      datedesicion: demande.datedesicion?.isValid() ? demande.datedesicion.format(DATE_FORMAT) : undefined,
      dateCreation: demande.dateCreation?.isValid() ? demande.dateCreation.format(DATE_FORMAT) : undefined,
      dateDepotSiteWeb: demande.dateDepotSiteWeb?.isValid() ? demande.dateDepotSiteWeb.format(DATE_FORMAT) : undefined,
      dateValidation: demande.dateValidation?.isValid() ? demande.dateValidation.format(DATE_FORMAT) : undefined,
      dateExport: demande.dateExport?.isValid() ? demande.dateExport.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.datedesicion = res.body.datedesicion ? dayjs(res.body.datedesicion) : undefined;
      res.body.dateCreation = res.body.dateCreation ? dayjs(res.body.dateCreation) : undefined;
      res.body.dateDepotSiteWeb = res.body.dateDepotSiteWeb ? dayjs(res.body.dateDepotSiteWeb) : undefined;
      res.body.dateValidation = res.body.dateValidation ? dayjs(res.body.dateValidation) : undefined;
      res.body.dateExport = res.body.dateExport ? dayjs(res.body.dateExport) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((demande: IDemande) => {
        demande.datedesicion = demande.datedesicion ? dayjs(demande.datedesicion) : undefined;
        demande.dateCreation = demande.dateCreation ? dayjs(demande.dateCreation) : undefined;
        demande.dateDepotSiteWeb = demande.dateDepotSiteWeb ? dayjs(demande.dateDepotSiteWeb) : undefined;
        demande.dateValidation = demande.dateValidation ? dayjs(demande.dateValidation) : undefined;
        demande.dateExport = demande.dateExport ? dayjs(demande.dateExport) : undefined;
      });
    }
    return res;
  }
}
