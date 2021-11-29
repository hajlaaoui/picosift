import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { DxButtonModule } from 'devextreme-angular/ui/button'
import { DxDataGridModule} from 'devextreme-angular/ui/data-grid';
import {DxBulletModule} from 'devextreme-angular/ui/bullet';
import {DxTemplateModule } from 'devextreme-angular';

@NgModule({
  imports: [SharedModule,DxButtonModule,DxDataGridModule,DxBulletModule,DxTemplateModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
})
export class HomeModule {}
