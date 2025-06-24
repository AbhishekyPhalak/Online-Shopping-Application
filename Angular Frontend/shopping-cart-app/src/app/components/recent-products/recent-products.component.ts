import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product.model';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-recent-products',
  templateUrl: './recent-products.component.html'
})
export class RecentProductsComponent implements OnInit {
  products: Product[] = [];

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.productService.getTopRecentProducts(3).subscribe({
      next: (res) => this.products = res.data,
      error: () => console.error('Failed to fetch recent products')
    });
  }
}
