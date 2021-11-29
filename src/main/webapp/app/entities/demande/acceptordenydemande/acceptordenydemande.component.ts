import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IDemande } from '../demande.model';
import { DemandeService } from '../service/demande.service';

@Component({
  selector: 'jhi-acceptordenydemande',
  templateUrl: './acceptordenydemande.component.html',
  styleUrls: ['./acceptordenydemande.component.scss']
})
export class AcceptordenydemandeComponent  {
  demande?: IDemande;
  constructor(protected demandeService: DemandeService,protected  activeModal: NgbActiveModal) { }


  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmdeny(id: number): void {
    this.demandeService.updatetodeny(id).subscribe(() => {
      this.activeModal.close('deny');
    });
  }
  confirmaccept(id: number): void {
    this.demandeService.updatetoaccept(id).subscribe(() => {
      this.activeModal.close('accept');
    });

}
}
