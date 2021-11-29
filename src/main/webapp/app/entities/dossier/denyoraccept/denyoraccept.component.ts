import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IDossier } from '../dossier.model';
import { DossierService } from '../service/dossier.service';

@Component({
  selector: 'jhi-denyoraccept',
  templateUrl: './denyoraccept.component.html',
  styleUrls: ['./denyoraccept.component.scss']
})
export class DenyoracceptComponent  {
  dossier?: IDossier;
  constructor(protected dossierService: DossierService,protected  activeModal: NgbActiveModal) {}



  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmdeny(id: number): void {
    this.dossierService.updatetodeny(id).subscribe(() => {
      this.activeModal.close('deny');
    });
  }
  confirmaccept(id: number): void {
    this.dossierService.updatetoaccept(id).subscribe(() => {
      this.activeModal.close('accept');
    });
  }










}
