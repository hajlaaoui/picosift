import * as dayjs from 'dayjs';
import { IDossier } from 'app/entities/dossier/dossier.model';
import { Statut } from 'app/entities/enumerations/statut.model';

export interface IDemande {
  id?: number;
  refSiteWeb?: string;
  datedesicion?: dayjs.Dayjs | null;
  importateur?: string | null;
  refBF?: string | null;
  marque?: string | null;
  modele?: string | null;
  numeroSerie?: string | null;
  imei1?: string | null;
  imei2?: string | null;
  imei3?: string | null;
  statut?: Statut | null;
  dateCreation?: dayjs.Dayjs | null;
  dateDepotSiteWeb?: dayjs.Dayjs | null;
  dateValidation?: dayjs.Dayjs | null;
  dateExport?: dayjs.Dayjs | null;
  commentaire?: string;
  rapport_date?: number;
  dossier?: IDossier | null;
}

export class Demande implements IDemande {
  constructor(
    public id?: number,
    public refSiteWeb?: string,
    public datedesicion?: dayjs.Dayjs | null,
    public importateur?: string | null,
    public refBF?: string | null,
    public marque?: string | null,
    public modele?: string | null,
    public numeroSerie?: string | null,
    public imei1?: string | null,
    public imei2?: string | null,
    public imei3?: string | null,
    public statut?: Statut | null,
    public dateCreation?: dayjs.Dayjs | null,
    public dateDepotSiteWeb?: dayjs.Dayjs | null,
    public dateValidation?: dayjs.Dayjs | null,
    public dateExport?: dayjs.Dayjs | null,
    public commentaire?: string,
    public rapport_date?: number,
    public dossier?: IDossier | null
  ) {}
}

export function getDemandeIdentifier(demande: IDemande): number | undefined {
  return demande.id;
}
