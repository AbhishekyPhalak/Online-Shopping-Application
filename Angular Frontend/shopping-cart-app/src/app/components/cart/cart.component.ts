import { Component, OnInit } from '@angular/core';
import { CartService, CartItem } from 'src/app/services/cart.service';
import { HttpClient } from '@angular/common/http';
import { ToastService } from 'src/app/services/toast.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  cart: CartItem[] = [];

  constructor(
    private cartService: CartService,
    private http: HttpClient,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.cart = this.cartService.getCart();
  }

  removeItem(productId: number) {
    this.cartService.removeFromCart(productId);
    this.cart = this.cartService.getCart();
    this.toastService.show('Item removed from cart.', 'success');
  }

  getTotal(): number {
  return this.cart.reduce((sum, item) => sum + item.retailPrice * item.quantity, 0);
}

  placeOrder() {
    if (this.cart.length === 0) {
      this.toastService.show('Cart is empty.', 'error');
      return;
    }

    this.http.post('http://localhost:8080/orders', { order: this.cart }).subscribe({
      next: (res: any) => {
        this.toastService.show(res.message, 'success');
        this.cartService.clearCart();
        this.cart = [];
      },
      error: (err) => {
        const message = err.error?.message || 'Order placement failed.';
        this.toastService.show(message, 'error');
      }
    });
  }
}
