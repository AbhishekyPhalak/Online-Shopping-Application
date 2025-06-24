import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product.model';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-admin-popular-products',
  templateUrl: './admin-popular-products.component.html',
  styleUrls: ['./admin-popular-products.component.css']
})
export class AdminPopularProductsComponent implements OnInit {

  products: Product[] = [];

  constructor(private productService: ProductService) { 
    
  }

  ngOnInit(): void {
    this.productService.getTopPopularProducts(3).subscribe({
      next: (res) => this.products = res.data,
      error: () => console.error('Failed to fetch profitable products')
    });
  }

}
