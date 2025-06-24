import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-admin-total-sold-products',
  templateUrl: './admin-total-sold-products.component.html',
  styleUrls: ['./admin-total-sold-products.component.css']
})
export class AdminTotalSoldProductsComponent implements OnInit {

  totalSold: number | null = null;
  error = '';

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.productService.getTotalSoldProducts().subscribe({
      next: (res) => {
        console.log(res)
        this.totalSold = res.data;
      },
      error: (err: HttpErrorResponse) => {
        console.log(err)
        this.error = 'Failed to fetch total sold products';
      }
    });
  }

}
