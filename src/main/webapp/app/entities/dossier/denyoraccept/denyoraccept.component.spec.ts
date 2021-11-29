import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DenyoracceptComponent } from './denyoraccept.component';

describe('DenyoracceptComponent', () => {
  let component: DenyoracceptComponent;
  let fixture: ComponentFixture<DenyoracceptComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DenyoracceptComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DenyoracceptComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
