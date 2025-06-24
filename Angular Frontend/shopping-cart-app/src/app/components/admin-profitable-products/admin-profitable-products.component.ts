import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product.model';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-admin-profitable-products',
  templateUrl: './admin-profitable-products.component.html',
  styleUrls: ['./admin-profitable-products.component.css']
})
export class AdminProfitableProductsComponent implements OnInit {
  products: Product[] = [];

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.productService.getTopProfitableProducts(3).subscribe({
      next: (res) => this.products = res.data,
      error: () => console.error('Failed to fetch profitable products')
    });
  }
}
