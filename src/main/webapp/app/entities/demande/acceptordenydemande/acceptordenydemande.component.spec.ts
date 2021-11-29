import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AcceptordenydemandeComponent } from './acceptordenydemande.component';

describe('AcceptordenydemandeComponent', () => {
  let component: AcceptordenydemandeComponent;
  let fixture: ComponentFixture<AcceptordenydemandeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AcceptordenydemandeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AcceptordenydemandeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
