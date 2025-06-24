import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FrequentProductsComponent } from './frequent-products.component';

describe('FrequentProductsComponent', () => {
  let component: FrequentProductsComponent;
  let fixture: ComponentFixture<FrequentProductsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FrequentProductsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FrequentProductsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
