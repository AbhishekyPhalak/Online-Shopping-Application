import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Product } from 'src/app/models/product.model';
import { ProductService } from 'src/app/services/product.service';
import { ToastService } from 'src/app/services/toast.service';
import { HttpErrorResponse } from '@angular/common/http';
import { WatchlistService } from 'src/app/services/watchlist.service';
import { CartService } from 'src/app/services/cart.service';
import {jwtDecode} from 'jwt-decode';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {
  product?: Product;
  quantity: number = 1;
  products: Product[] = [];
  isAdmin: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private toastService: ToastService,
    private watchlistService: WatchlistService,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    const token = localStorage.getItem('jwtToken');
    if (token) {
      const decoded = jwtDecode<any>(token);
      this.isAdmin = decoded.role !== 'user';
    }

    const productId = Number(this.route.snapshot.paramMap.get('id'));
    this.productService.getProductById(productId).subscribe({
      next: (res) => {
        this.product = res.data;
      },
      error: (err: HttpErrorResponse) => {
        if (err.status === 404) {
          this.toastService.show('Product not found.', 'error');
        } else if (err.status === 403) {
          this.toastService.show('You are not authorized to view this product.', 'error');
        } else {
          this.toastService.show('Something went wrong. Please try again later.', 'error');
        }
      }
    });
  }

  addToCart() {
    if (!this.product || this.quantity <= 0) return;

    this.cartService.addToCart({
      productId: this.product.productId,
      name: this.product.name,
      description: this.product.description,
      retailPrice: this.product.retailPrice,
      quantity: this.quantity
    });

    this.toastService.show('Added to cart!', 'success');
  }


  addToWatchlist() {
    if (!this.product) return;

    this.watchlistService.addToWatchlist(this.product.productId).subscribe({
      next: (res) => this.toastService.show(res.message, 'success'),
      error: (err: HttpErrorResponse) => {
        if (err.status === 404) {
          this.toastService.show('Watchlist entry not found.', 'error');
        } else {
          this.toastService.show('Failed to add to watchlist.', 'error');
        }
      }
    });
  }

  removeFromWatchlist() {
    if (!this.product) return;

    this.watchlistService.removeFromWatchlist(this.product.productId).subscribe({
      next: (res) => this.toastService.show(res.message, 'success'),
      error: (err: HttpErrorResponse) => {
        if (err.status === 404) {
          this.toastService.show('Watchlist entry not found.', 'error');
        } else {
          this.toastService.show('Failed to remove from watchlist.', 'error');
        }
      }
    });
  }


}
