import { Component, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ChartdemandeComponent } from './demande/chartdemande/chartdemande.component';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'dossier',
        data: { pageTitle: 'Dossiers' },
        loadChildren: () => import('./dossier/dossier.module').then(m => m.DossierModule),
      },
      {
        path: 'demande',
        data: { pageTitle: 'Demandes' },
        loadChildren: () => import('./demande/demande.module').then(m => m.DemandeModule),
      },
      { path: 'rapport', component: ChartdemandeComponent },

      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],

})
export class EntityRoutingModule {}
