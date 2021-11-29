import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DemandeComponent } from './list/demande.component';
import { DemandeDetailComponent } from './detail/demande-detail.component';
import { DemandeUpdateComponent } from './update/demande-update.component';
import { DemandeDeleteDialogComponent } from './delete/demande-delete-dialog.component';
import { DemandeRoutingModule } from './route/demande-routing.module';
import { DxButtonModule } from 'devextreme-angular/ui/button'
import { DxDataGridModule} from 'devextreme-angular/ui/data-grid';
import {DxBulletModule} from 'devextreme-angular/ui/bullet';
import {DxTemplateModule } from 'devextreme-angular';
import { AcceptordenydemandeComponent } from './acceptordenydemande/acceptordenydemande.component';
import { ChartdemandeComponent } from './chartdemande/chartdemande.component';


@NgModule({
  imports: [DxButtonModule,DxDataGridModule,DxBulletModule,DxTemplateModule,SharedModule, DemandeRoutingModule],
  declarations: [DemandeComponent, DemandeDetailComponent, DemandeUpdateComponent, DemandeDeleteDialogComponent, ChartdemandeComponent],
  entryComponents: [DemandeDeleteDialogComponent],
})
export class DemandeModule {}
