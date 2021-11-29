import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';

import { DxButtonModule } from 'devextreme-angular/ui/button'
import { DxDataGridModule} from 'devextreme-angular/ui/data-grid';
import {DxBulletModule} from 'devextreme-angular/ui/bullet';
import {DxTemplateModule } from 'devextreme-angular';
import { HOME_ROUTE } from 'app/home/home.route';
import { HomeComponent } from 'app/home/home.component';
import { AcceptordenydemandeComponent } from '../acceptordenydemande/acceptordenydemande.component';

@NgModule({
  imports: [SharedModule,DxButtonModule,DxDataGridModule,DxBulletModule,DxTemplateModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
})
export class DemandeModule {}
