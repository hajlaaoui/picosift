import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDossier } from '../dossier.model';

import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/config/pagination.constants';
import { DossierService } from '../service/dossier.service';
import { DossierDeleteDialogComponent } from '../delete/dossier-delete-dialog.component';
import { AccountService } from 'app/core/auth/account.service';
import { DenyoracceptComponent } from '../denyoraccept/denyoraccept.component';

@Component({
  selector: 'jhi-dossier',
  templateUrl: './dossier.component.html',
  styleUrls: ['./dossier.component.css'],
})
export class DossierComponent implements OnInit {
  dossiers?: IDossier[];
  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page?: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected dossierService: DossierService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal,
    protected service: AccountService
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    this.isLoading = true;
    const pageToLoad: number = page ?? this.page ?? 1;

    this.dossierService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IDossier[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate);
        },
        () => {
          this.isLoading = false;
          this.onError();
        }
      );
  }

  ngOnInit(): void {
    this.handleNavigation();
      // eslint-disable-next-line no-console
    console.log("admin");
    this.service.getAuthenticationState().toPromise().then((rest:any)=>{
      // eslint-disable-next-line no-console
      console.log("admin  : " , rest);
    });
  }

  trackId(index: number, item: IDossier): number {
    return item.id!;
  }

  delete(dossier: IDossier): void {
    const modalRef = this.modalService.open(DossierDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dossier = dossier;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadPage();
      }
    });
  }
  deny(dossier: IDossier): void {
    const modalRef = this.modalService.open(DenyoracceptComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dossier = dossier;
    // unsubscribe not needed because closed completes on modal close
    this.service.getAuthenticationState().toPromise();
    modalRef.closed.subscribe(reason => {
      if (reason === 'deny') {
        this.loadPage();
      }
    });

  }
  accept(dossier: IDossier): void {
    const modalRef = this.modalService.open(DenyoracceptComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dossier = dossier;
    // unsubscribe not needed because closed completes on modal close
    this.service.getAuthenticationState().toPromise();
    modalRef.closed.subscribe(reason => {
      if (reason === 'accept') {
        this.loadPage();
      }
    });

  }






  update(dossier: IDossier): void {
    const modalRef = this.modalService.open(DossierDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dossier = dossier;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
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



  protected onSuccess(data: IDossier[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/dossier'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? ASC : DESC),
        },
      });
    }
    this.dossiers = data ?? [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
