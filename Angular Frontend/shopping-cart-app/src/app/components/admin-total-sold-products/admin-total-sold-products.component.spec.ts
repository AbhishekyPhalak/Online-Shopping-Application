import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminTotalSoldProductsComponent } from './admin-total-sold-products.component';

describe('AdminTotalSoldProductsComponent', () => {
  let component: AdminTotalSoldProductsComponent;
  let fixture: ComponentFixture<AdminTotalSoldProductsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminTotalSoldProductsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminTotalSoldProductsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
