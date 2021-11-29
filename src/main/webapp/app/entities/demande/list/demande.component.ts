/* eslint-disable @typescript-eslint/explicit-function-return-type */
import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDemande } from '../demande.model';
import { DemandeService } from '../service/demande.service';
import { DemandeDeleteDialogComponent } from '../delete/demande-delete-dialog.component';
import { DxDataGridModule,
  DxBulletModule,
  DxTemplateModule } from 'devextreme-angular';
import DataSource from 'devextreme/data/data_source';
import { Service } from '../service/app.service';
import { AcceptordenydemandeComponent } from '../acceptordenydemande/acceptordenydemande.component';
import { AccountService } from 'app/core/auth/account.service';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/config/pagination.constants';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { combineLatest } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'jhi-demande',
  templateUrl: './demande.component.html',
  styleUrls: ['./demande.component.css'],
  providers: [Service]

})

export class DemandeComponent implements OnInit {

  demandes?: IDemande[];
  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page?: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  dataSource: any ;
  collapsed = false;
  contentReady = (e:any) => {
    if(!this.collapsed) {
        this.collapsed = true;
        e.component.expandRow(["EnviroCare"]);
    }
};
loadPage(page?: number, dontNavigate?: boolean): void {
  this.isLoading = true;
  const pageToLoad: number = page ?? this.page ?? 1;

  this.demandeService
    .query({
      page: pageToLoad - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    })
    .subscribe(
      (res: HttpResponse<IDemande[]>) => {
        this.isLoading = false;
        this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate);
      },
      () => {
        this.isLoading = false;
        this.onError();
      }
    );
}

  // eslint-disable-next-line @typescript-eslint/member-ordering
  constructor(protected demandeService: DemandeService, protected modalService: NgbModal,
    service: Service,protected services: AccountService,
     protected activatedRoute: ActivatedRoute,
    protected router: Router,) {
    //this.dataSource = service.getDataSource();
  }

  loadAll(): void {
    this.isLoading = true;

    this.demandeService.query().subscribe(
      (res: HttpResponse<IDemande[]>) => {
        this.isLoading = false;
        this.demandes = res.body ?? [];
        this.dataSource = res.body ;
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IDemande): number {
    return item.id!;
  }

  delete(demande: IDemande): void {
    const modalRef = this.modalService.open(DemandeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.demande = demande;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
  deny(demande: IDemande): void {
    const modalRef = this.modalService.open(AcceptordenydemandeComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.demande = demande;
    // unsubscribe not needed because closed completes on modal close
    this.services.getAuthenticationState().toPromise();
    modalRef.closed.subscribe(reason => {
      if (reason === 'deny') {
        this.loadPage();
      }
    });

  }
  calculintervale(demande: IDemande,id:number): void {

    // unsubscribe not needed because closed completes on modal close
    this.services.getAuthenticationState().toPromise();
    //this.demandeService.calculintervale(id).subscribe(res =>);



  }
  accept(demande: IDemande): void {
    const modalRef = this.modalService.open(AcceptordenydemandeComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.demande = demande;
    // unsubscribe not needed because closed completes on modal close
    this.services.getAuthenticationState().toPromise();
    modalRef.closed.subscribe(reason => {
      if (reason === 'accept') {
        this.loadPage();
      }
    });
}

  protected sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? ASC : DESC)];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected handleNavigation(): void {
    combineLatest([this.activatedRoute.data, this.activatedRoute.queryParamMap]).subscribe(([data, params]) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get(SORT) ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === ASC;
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    });
  }



  protected onSuccess(data: IDemande[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/demande'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? ASC : DESC),
        },
      });
    }
    this.demandes = data ?? [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }


}

