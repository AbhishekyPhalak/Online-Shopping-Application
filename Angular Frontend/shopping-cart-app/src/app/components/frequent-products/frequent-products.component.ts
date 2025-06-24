import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product.model';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-frequent-products',
  templateUrl: './frequent-products.component.html'
})
export class FrequentProductsComponent implements OnInit {
  products: Product[] = [];

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.productService.getTopFrequentProducts(3).subscribe({
      next: (res) => this.products = res.data,
      error: () => console.error('Failed to fetch frequent products')
    });
  }
}
