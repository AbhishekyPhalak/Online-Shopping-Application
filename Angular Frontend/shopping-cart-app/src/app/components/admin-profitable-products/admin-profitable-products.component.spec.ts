import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminProfitableProductsComponent } from './admin-profitable-products.component';

describe('AdminProfitableProductsComponent', () => {
  let component: AdminProfitableProductsComponent;
  let fixture: ComponentFixture<AdminProfitableProductsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminProfitableProductsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminProfitableProductsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
