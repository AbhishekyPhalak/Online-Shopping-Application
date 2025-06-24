import { Component, OnInit } from '@angular/core';
import { WatchlistService } from 'src/app/services/watchlist.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ToastService } from 'src/app/services/toast.service';

interface Product {
  productId: number;
  name: string;
  description: string;
  retailPrice: number;
}

@Component({
  selector: 'app-watchlist',
  templateUrl: './watchlist.component.html',
  styleUrls: ['./watchlist.component.css']
})
export class WatchlistComponent implements OnInit {
  products: Product[] = [];

  constructor(private watchlistService: WatchlistService, private toastService: ToastService,) {}

  ngOnInit(): void {
    this.watchlistService.getAllWatchlistItems().subscribe({
      next: (res) => this.products = res.data,
      error: (err: HttpErrorResponse) => {
        console.error(err);
      }
    });
  }

  viewProduct(productId: number) {
    // You can route to product page or show modal
    console.log('View product', productId);
  }

  removeFromWatchlist(id: number) {

    this.watchlistService.removeFromWatchlist(id).subscribe({
      next: (res) => {
        this.toastService.show(res.message, 'success')
        this.watchlistService.getAllWatchlistItems().subscribe({
          next: (res) => this.products = res.data,
          error: (err: HttpErrorResponse) => {
            console.error(err);
        }
    });
      
      },
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
