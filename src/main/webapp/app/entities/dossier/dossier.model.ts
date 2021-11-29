import * as dayjs from 'dayjs';
import { IDemande } from 'app/entities/demande/demande.model';
import { Statut } from 'app/entities/enumerations/statut.model';

export interface IDossier {
  id?: number;
  refBF?: string;
  datedesicion?: dayjs.Dayjs | null;
  cin?: string;
  passeport?: string | null;
  importateur?: string | null;
  marque?: string | null;
  statut?: Statut | null;
  numAvisArrive?: string | null;
  dateValidation?: dayjs.Dayjs | null;
  commentaire?: string;
  demandes?: IDemande[] | null;
}

export class Dossier implements IDossier {
  constructor(
    public id?: number,
    public refBF?: string,
    public datedesicion?: dayjs.Dayjs | null,
    public cin?: string,
    public passeport?: string | null,
    public importateur?: string | null,
    public marque?: string | null,
    public statut?: Statut | null,
    public numAvisArrive?: string | null,
    public dateValidation?: dayjs.Dayjs | null,
    public commentaire?: string,
    public demandes?: IDemande[] | null
  ) {}
}

export function getDossierIdentifier(dossier: IDossier): number | undefined {
  return dossier.id;
}
